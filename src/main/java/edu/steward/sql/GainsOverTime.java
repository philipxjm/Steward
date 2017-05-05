package edu.steward.sql;

import static edu.steward.sql.DatabaseApi.initializePrices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.TreeMultimap;

import edu.steward.stock.Stock;
import edu.steward.stock.Fundamentals.Gains;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.user.Holding;

/**
 * Created by mrobins on 5/2/17.
 */
public class GainsOverTime {

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";
  private static String quoteUrl = base + "data/quotes.sqlite3";
  private static final int secInDay = 86400;

  public static List<Gains> getGainsPortfolioGraph(String portId) {
    TreeMultimap<String, Holding> trans = getTransactionHistory(portId);
    if (trans.size() == 0) {
      return new ArrayList<>();
    }
    long startTime = Long.MAX_VALUE;
    for (String ticker : trans.keySet()) {
      NavigableSet<Holding> holdings = trans.get(ticker);
      Holding firstHolding = holdings.first();
      if (firstHolding.getTime() < startTime) {
        startTime = firstHolding.getTime();
      }
    }

    Set<String> tickers = getStocksFromPortfolio(portId);
    Map<String, List<Price>> prices = getPrices(tickers, startTime);
    for (String ticker : tickers) {
      Stock s = new Stock(ticker);
      Price p = s.getCurrPrice();
      prices.get(ticker).add(p);
    }
    List<Long> times = new ArrayList<>();
    for (Price p : prices.get(tickers.iterator().next())) {
      times.add(p.getTime());
    }
    Collections.sort(times);
    ListMultimap<String, Integer> quantities = getQuantityOverTime(trans,
        times);
    List<List<Double>> buySell = getBuySellOverTime(trans, times);

    List<Gains> ret = new ArrayList<>();
    int c = 0;
    for (long time : times) {
      List<Double> temp = buySell.get(c);
      double B = temp.get(0);
      double S = temp.get(1);
      double assetValue = 0;
      for (String ticker : tickers) {
        assetValue += prices.get(ticker).get(c).getValue()
            * quantities.get(ticker).get(c);
      }

      double percentage = ((S + assetValue) - B)
          / buySell.get(buySell.size() - 1).get(0);
      ret.add(new Gains(percentage, time));
      c += 1;
    }
    return ret;
  }

  private static TreeMultimap<String, Holding> getTransactionHistory(
      String portId) {
    TreeMultimap<String, Holding> transactionHistory = TreeMultimap
        .create(new Comparator<String>() {
          @Override
          public int compare(String o1, String o2) {
            return o1.compareTo(o2);
          }
        }, new Comparator<Holding>() {
          @Override
          public int compare(Holding o1, Holding o2) {
            return Long.compare(o1.getTime(), o2.getTime());
          }
        });
    String query = "SELECT stock, time, trans, price FROM History "
        + "WHERE portfolio = ? ORDER BY time ASC;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            Integer time = Integer.parseInt(rs.getString(2));
            Integer trans = Integer.parseInt(rs.getString(3));
            Double price = Double.parseDouble(rs.getString(4));

            Holding holding = new Holding(ticker, trans, time, price);
            transactionHistory.put(ticker, holding);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return transactionHistory;
  }

  private static Map<String, List<Price>> getPrices(Set<String> tickers,
      long startTime) {
    // TODO: Have this method get the historical prices for a set of stocks
    // after a given start time
    Map<String, List<Price>> ret = new HashMap<>();
    String query = "SELECT time, price FROM quotes " + "WHERE stock = ? "
        + "AND time >= ? ORDER BY time ASC;";
    try (Connection c = DriverManager.getConnection(quoteUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setLong(2, startTime);
        for (String ticker : tickers) {
          List<Price> prices = new ArrayList<>();
          prep.setString(1, ticker);
          try (ResultSet rs = prep.executeQuery()) {
            while (rs.next()) {
              Long priceTime = Long.parseLong(rs.getString(1));
              System.out.println(priceTime > startTime);
              Double priceVal = Double.parseDouble(rs.getString(2));
              Price price = new Price(priceVal, priceTime);
              prices.add(price);
            }
            if (prices.size() == 0) {
              List<Price> temp = initializePrices(ticker);
              // TODO: Could this be more efficient?
              for (Price p : temp) {
                if (p.getTime() >= startTime) {
                  prices.add(p);
                }
              }

            }
            ret.put(ticker, prices);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return ret;
  }

  private static ListMultimap<String, Integer> getQuantityOverTime(
      TreeMultimap<String, Holding> transHist,
      // TODO: This should derive from the timestamps on the prices
      List<Long> times) {
    ListMultimap<String, Integer> ret = ArrayListMultimap.create();
    long lastTime = 0;
    for (Long time : times) {
      for (String ticker : transHist.keySet()) {
        NavigableSet<Holding> holdings = transHist.get(ticker);
        Holding h = new Holding("", 0, time);
        if (!ret.containsKey(ticker)) {
          lastTime = time;
          NavigableSet<Holding> pastTrans = holdings.headSet(h, true);
          int total = 0;
          for (Holding holding : pastTrans) {
            total += holding.getShares();

          }
          ret.put(ticker, total);
        } else {
          int currTotal = Iterables.getLast(ret.get(ticker));
          Holding hMostRecent = new Holding("", 0, lastTime);
          NavigableSet<Holding> sinceLastTrans = holdings.subSet(hMostRecent,
              false, h, true);
          for (Holding holding : sinceLastTrans) {
            currTotal += holding.getShares();
          }
          lastTime = time;
          ret.put(ticker, currTotal);
        }
      }
    }
    return ret;
  }

  private static List<List<Double>> getBuySellOverTime(
      TreeMultimap<String, Holding> transHist,
      // TODO: This should derive from the timestamps on the prices
      List<Long> times) {
    List<List<Double>> ret = new ArrayList<>();
    long lastTime = 0;
    for (Long time : times) {
      double boughtTotal = 0;
      double sellTotal = 0;
      if (ret.size() != 0) {
        List<Double> currTotal = Iterables.getLast(ret);
        boughtTotal = currTotal.get(0);
        sellTotal = currTotal.get(1);
      }
      for (String ticker : transHist.keySet()) {
        NavigableSet<Holding> holdings = transHist.get(ticker);
        Holding h = new Holding("", 0, time);
        if (ret.size() == 0) {
          lastTime = time;
          NavigableSet<Holding> pastTrans = holdings.headSet(h, true);
          for (Holding holding : pastTrans) {
            if (holding.getShares() > 0) { // Buy
              boughtTotal += holding.getShares() * holding.getPrice();
            } else { // Sell
              sellTotal -= holding.getShares() * holding.getPrice();
            }
          }
        } else {
          Holding hMostRecent = new Holding("", 0, lastTime);
          NavigableSet<Holding> sinceLastTrans = holdings.subSet(hMostRecent,
              false, h, true);
          for (Holding holding : sinceLastTrans) {
            if (holding.getShares() > 0) { // Buy
              boughtTotal += holding.getShares() * holding.getPrice();
            } else { // Sell
              sellTotal -= holding.getShares() * holding.getPrice();
            }
          }
          lastTime = time;
        }
      }
      ret.add(ImmutableList.of(boughtTotal, sellTotal));
    }
    return ret;
  }

  public static Set<String> getStocksFromPortfolio(String portId) {
    Set<String> ret = new HashSet<>();
    String query = "SELECT stock FROM History " + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            ret.add(ticker);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ret;
  }

  /**
   * Gets a list of time intervals to show for the unrealized gains graph.
   * 
   * @param portId
   *          Id of the user portfolio.
   * @return A list of unix timestamps that indicate at which times unrealized
   *         gains are to be retrieved.
   */
  private static List<Integer> getPortfolioTimes(String portId) {
    Integer currTime = (int) (System.currentTimeMillis() / 1000L);
    String query = "SELECT time FROM History " + "WHERE portfolio = ?;";
    List<Integer> times = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            Integer time = Integer.parseInt(rs.getString(1));
            times.add(time);

          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Collections.sort(times);
    Integer firstTime = times.get(0);
    List<Integer> ret = new ArrayList<>();
    while (firstTime < currTime) {
      ret.add(firstTime);
      firstTime += secInDay;
    }
    // TODO: Append the current time at the very end of the process, not now.
    // TODO: This is bc the getCurrPrice method is more reliable than the
    // getPrice.
    return ret;
  }

  /**
   * Gets a list of time intervals to show for the unrealized gains graph.
   * 
   * @param portId
   *          Id of the user portfolio.
   * @return A list of unix timestamps that indicate at which times unrealized
   *         gains are to be retrieved.
   */
  private static List<Integer> getStockTimes(String portId, String ticker) {
    Integer currTime = (int) (System.currentTimeMillis() / 1000L);
    String query = "SELECT time FROM History " + "WHERE portfolio = ? "
        + "AND stock = ?;";
    List<Integer> times = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        prep.setString(2, ticker);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            Integer time = Integer.parseInt(rs.getString(1));
            times.add(time);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Collections.sort(times);
    Integer firstTime = times.get(0);
    List<Integer> ret = new ArrayList<>();
    while (firstTime < currTime) {
      ret.add(firstTime);
      firstTime += secInDay;
    }
    // TODO: Append the current time at the very end of the process, not now.
    // TODO: This is bc the getCurrPrice method is more reliable than the
    // getPrice.
    return ret;
  }

  /**
   * Gets the unrealized gains from a specific stock in a user's portfolio at a
   * given time.
   * 
   * @param portId
   *          The id of the portfolio.
   * @param ticker
   *          The ticker of the stock in the portfolio.
   * @param time
   *          The time at which to get unrealized gains.
   * @return The unrealized gains of the specified stock in the specified
   *         portfolio at the given time.
   */
  private static Double getGainsStock(String portId, String ticker,
      Integer time) {
    Double purchasedCost = 0.0;
    Double soldCost = 0.0;
    Double quanityHeld = 0.0;
    String query = "SELECT trans, price FROM History " + "WHERE portfolio = ? "
        + "AND time >= ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        prep.setInt(2, time);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            Integer trans = Integer.parseInt(rs.getString(1));
            Double price = Double.parseDouble(rs.getString(2));

            // Keeps track of the quantity held of the stock.
            quanityHeld += trans;

            // Purchase of stocks.
            if (trans > 0) {
              purchasedCost += trans * price;
            }
            // Sale of stocks.
            else if (trans < 0) {
              soldCost += -trans * price;
            }

          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Stock stock = new Stock(ticker);
    // Price of stock at specified time.
    Price price = stock.getPrice(time);
    Double pval = price.getValue();
    /*
     * Unrealized value of all assets and sales divide by all purchases.
     * Subtracted 1 so it is a percent. I.e. 120/100 - 1 = 0.20 = 20 percent
     * unrealized gains
     */
    return (((quanityHeld * pval + soldCost) / purchasedCost) - 1);
  }

  /**
   * Gets the unrealized gains from a specific stock in a user's portfolio for
   * the CURRENT TIME.
   * 
   * @param portId
   *          The id of the portfolio.
   * @param ticker
   *          The ticker of the stock in the portfolio.
   * @return The unrealized gains of the specified stock in the specified
   *         portfolio at the given time.
   */
  private static Double getGainsStock(String portId, String ticker) {
    Double purchasedCost = 0.0;
    Double soldCost = 0.0;
    Double quanityHeld = 0.0;
    String query = "SELECT trans, price FROM History " + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            Integer trans = Integer.parseInt(rs.getString(1));
            Double price = Double.parseDouble(rs.getString(2));

            // Keeps track of the quantity held of the stock.
            quanityHeld += trans;

            // Purchase of stocks.
            if (trans > 0) {
              purchasedCost += trans * price;
            }
            // Sale of stocks.
            else if (trans < 0) {
              soldCost += -trans * price;
            }

          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Stock stock = new Stock(ticker);
    // Price of stock at specified time.
    Price price = stock.getCurrPrice();
    Double pval = price.getValue();
    /*
     * Unrealized value of all assets and sales divide by all purchases.
     * Subtracted 1 so it is a percent. I.e. 120/100 - 1 = 0.20 = 20 percent
     * unrealized gains
     */
    return (((quanityHeld * pval + soldCost) / purchasedCost) - 1);
  }

  private static Double getGainsPortfolio(String portId, Integer time) {
    String query = "SELECT stock FROM History " + "WHERE portfolio = ?;";
    Set<String> stockTickers = new HashSet<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            stockTickers.add(ticker);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Double gains = 0.0;
    for (String ticker : stockTickers) {
      gains += getGainsStock(portId, ticker, time);
    }
    return gains;
  }

  private static Double getGainsPortfolio(String portId) {
    String query = "SELECT stock FROM History " + "WHERE portfolio = ?;";
    Set<String> stockTickers = new HashSet<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            stockTickers.add(ticker);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Double gains = 0.0;
    for (String ticker : stockTickers) {
      gains += getGainsStock(portId, ticker);
    }
    return gains;
  }

}
