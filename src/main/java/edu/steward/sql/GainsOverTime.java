package edu.steward.sql;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.steward.stock.Fundamentals.Gains;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.Stock;
import edu.steward.user.Holding;
import edu.steward.user.Portfolio;

import java.sql.*;
import java.util.*;

/**
 * Created by mrobins on 5/2/17.
 */
public class GainsOverTime {

  enum INTERVAL {
    SHORT_TERM,
    LONG_TERM
  }

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";
  private static final int secInDay = 86400;



  public static List<Gains> getGainsPortfolioGraph(String portId) {
    List<Gains> ret = new ArrayList<>();
    List<Integer> times = getPortfolioTimes(portId);
//    Adds historic gains
    for (Integer time : times) {
      Gains gains = new Gains(getGainsPortfolio(portId, time) , (long) time);
      ret.add(gains);
    }
//    Adds current gains (at the real time)
    Long currTime = System.currentTimeMillis() / 1000L;
    Gains g = new Gains(getGainsPortfolio(portId), currTime);
    ret.add(g);
    return ret;
  }

  private static Gains getUnrealizedGains(String portId, INTERVAL interval) {
    Map<String, Integer> stockQuantities = new HashMap<>();
    Multimap<String, Holding> holdingHistory = TreeMultimap.create(new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    }, new Comparator<Holding>() {
      @Override
      public int compare(Holding o1, Holding o2) {
        return o1.getTime() - o2.getTime();
      }
    });
    Double purchasedCost = 0.0;
    Double soldCost = 0.0;
    String query1 = "";
    String query2 = "";
    switch (interval) {
      case SHORT_TERM:
        query1 = "SELECT stock, time, trans, price FROM History "
                + "WHERE portfolio = ?;";
        break;
      case LONG_TERM:
        query1 = "SELECT stock, time, trans, price FROM History "
                + "WHERE portfolio = ?;";
        break;
    }
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query1)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            Integer time = Integer.parseInt(rs.getString(2));
            Integer trans = Integer.parseInt(rs.getString(3));
            Double price = Double.parseDouble(rs.getString(4));


//            Keeps track of the quantity held of the stock.
            if (stockQuantities.get(ticker) == null) {
              stockQuantities.put(ticker, trans);
            } else {
              Integer quantity = stockQuantities.get(ticker);
              stockQuantities.replace(ticker, quantity + trans);
            }

            Integer updatedQuantity = stockQuantities.get(ticker);
            Holding holding = new Holding(ticker, updatedQuantity, time);
            holdingHistory.put(ticker, holding);

//            Purchase of stocks.
            if (trans > 0) {
              purchasedCost += trans * price;
            }
//            Sale of stocks.
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
  }

  /**
   * Gets a list of time intervals to show for the unrealized gains graph.
   * @param portId Id of the user portfolio.
   * @return A list of unix timestamps that indicate at which times unrealized
   * gains are to be retrieved.
   */
  private static List<Integer> getPortfolioTimes(String portId) {
    Integer currTime = (int) (System.currentTimeMillis() / 1000L);
    String query = "SELECT time FROM History "
            + "WHERE portfolio = ?;";
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
//    TODO: Append the current time at the very end of the process, not now.
//    TODO: This is bc the getCurrPrice method is more reliable than the getPrice.
    return ret;
  }

  /**
   * Gets a list of time intervals to show for the unrealized gains graph.
   * @param portId Id of the user portfolio.
   * @return A list of unix timestamps that indicate at which times unrealized
   * gains are to be retrieved.
   */
  private static List<Integer> getStockTimes(String portId, String ticker) {
    Integer currTime = (int) (System.currentTimeMillis() / 1000L);
    String query = "SELECT time FROM History "
            + "WHERE portfolio = ? "
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
//    TODO: Append the current time at the very end of the process, not now.
//    TODO: This is bc the getCurrPrice method is more reliable than the getPrice.
    return ret;
  }

  /**
   * Gets the unrealized gains from a specific stock in a user's portfolio
   * at a given time.
   * @param portId The id of the portfolio.
   * @param ticker The ticker of the stock in the portfolio.
   * @param time The time at which to get unrealized gains.
   * @return The unrealized gains of the specified stock in the specified
   * portfolio at the given time.
   */
  private static Double getGainsStock(
          String portId,
          String ticker,
          Integer time
  ) {
    Double purchasedCost = 0.0;
    Double soldCost = 0.0;
    Double quanityHeld = 0.0;
    String query = "SELECT trans, price FROM History "
            + "WHERE portfolio = ? "
            + "AND time <= ?;";
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

//            Keeps track of the quantity held of the stock.
            quanityHeld += trans;

//            Purchase of stocks.
            if (trans > 0) {
              purchasedCost += trans * price;
            }
//            Sale of stocks.
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
//    Price of stock at specified time.
    Price price = stock.getPrice(time);
    Double pval = price.getValue();
    /*
    Unrealized value of all assets and sales divide by all purchases.
    Subtracted 1 so it is a percent.
    I.e. 120/100 - 1 = 0.20 = 20 percent unrealized gains
     */
    return (((quanityHeld * pval + soldCost) / purchasedCost) - 1);
  }

  /**
   * Gets the unrealized gains from a specific stock in a user's portfolio
   * for the CURRENT TIME.
   * @param portId The id of the portfolio.
   * @param ticker The ticker of the stock in the portfolio.
   * @return The unrealized gains of the specified stock in the specified
   * portfolio at the given time.
   */
  private static Double getGainsStock(
          String portId,
          String ticker
  ) {
    Double purchasedCost = 0.0;
    Double soldCost = 0.0;
    Double quanityHeld = 0.0;
    String query = "SELECT trans, price FROM History "
            + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            Integer trans = Integer.parseInt(rs.getString(1));
            Double price = Double.parseDouble(rs.getString(2));

//            Keeps track of the quantity held of the stock.
            quanityHeld += trans;

//            Purchase of stocks.
            if (trans > 0) {
              purchasedCost += trans * price;
            }
//            Sale of stocks.
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
//    Price of stock at specified time.
    Price price = stock.getCurrPrice();
    Double pval = price.getValue();
    /*
    Unrealized value of all assets and sales divide by all purchases.
    Subtracted 1 so it is a percent.
    I.e. 120/100 - 1 = 0.20 = 20 percent unrealized gains
     */
    return (((quanityHeld * pval + soldCost) / purchasedCost) - 1);
  }

  private static Double getGainsPortfolio(
          String portId,
          Integer time
  ) {
    String query = "SELECT stock FROM History "
            + "WHERE portfolio = ?;";
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

  private static Double getGainsPortfolio(
          String portId
  ) {
    String query = "SELECT stock FROM History "
            + "WHERE portfolio = ?;";
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
