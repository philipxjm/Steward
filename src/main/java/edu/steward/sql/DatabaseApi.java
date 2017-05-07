package edu.steward.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import edu.steward.pools.Pool;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.user.Portfolio;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by kjin on 4/24/17.
 */
public class DatabaseApi {

  static {
    YahooFinance.logger.setLevel(Level.OFF);
  }

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";
  private static String quoteUrl = base + "data/quotes.sqlite3";

  public static boolean createUser(String userId, String name, String email,
      String pic) {
    String query = "INSERT INTO Users VALUES (?, ?, ?, ?);";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, userId);
        prep.setString(2, name);
        prep.setString(3, pic);
        prep.setString(4, email);
        prep.executeUpdate();

      } catch (SQLException e) {
        // User already exists
        e.printStackTrace();
        return false;
      }
    } catch (SQLException e) {
      // Idk man
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static Map<String, String> getUserInfo(String userId) {
    String query = "SELECT * FROM Users WHERE UserId = ?";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");

      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, userId);
        try (ResultSet rs = prep.executeQuery()) {
          rs.next();
          String name = rs.getString(2);
          String pic = rs.getString(3);
          String email = rs.getString(4);
          return ImmutableMap.of("user", name, "pic", pic, "email", email, "id",
              userId);
        } catch (SQLException e) {
          e.printStackTrace();
          return null;
        }
      } catch (SQLException e) {
        // User already exists
        return null;
      }
    } catch (SQLException e) {
      // Idk man
      e.printStackTrace();
      return null;
    }
  }

  public static List<Portfolio> getPortfoliosFromUser(String userId) {
    String query = "SELECT Name, PortfolioId FROM UserPortfolios "
        + "WHERE UserId = ? AND PoolId IS NULL;";
    List<Portfolio> portfolios = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, userId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String name = rs.getString(1);
            String id = rs.getString(2);
            Portfolio port = new Portfolio(name, id);
            portfolios.add(port);
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
    return portfolios;
  }

  public static Map<String, Portfolio> getAllPorts(String userId) {
    String query = "SELECT Name, PortfolioId FROM UserPortfolios "
        + "WHERE UserId = ?";
    HashMap<String, Portfolio> portfolios = new HashMap<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, userId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String name = rs.getString(1);
            String id = rs.getString(2);
            Portfolio port = new Portfolio(name, id);
            portfolios.put(id, port);
            System.out.println(id);
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
    return portfolios;
  }

  public static boolean createPortfolio(String userId, String portId,
      String portName, double initialBalance) {

    String stat = "INSERT INTO UserPortfolios VALUES (?, ?, ?, ?);";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setNull(4, 0);
        prep.setString(3, userId);
        prep.setString(2, portName);
        prep.setString(1, portId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
        // Portfolio already exists
        return false;
      }
      stat = "INSERT INTO Balances VALUES (?, ?)";
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setString(1, portId);
        prep.setDouble(2, initialBalance);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  public static boolean createPortfolio(String userId, String portId,
      String portName) {
    return createPortfolio(userId, portId, portName, 1000000);
  }

  public static boolean renamePortfolio(String userId, String oldName,
      String newName) {
    String stat = "UPDATE UserPortfolios SET Name=?,PortfolioId=? WHERE PortfolioId=?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        String oldPortId = userId + "/" + oldName;
        String newPortId = userId + "/" + newName;
        prep.setString(1, newName);
        prep.setString(2, newPortId);
        prep.setString(3, oldPortId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean removePortfolio(String userId, String portName) {
    String stat = "DELETE FROM UserPortfolios WHERE PortfolioId = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        String portId = userId + "/" + portName;
        prep.setString(1, portId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean stockTransaction(String portId, String ticker,
      int amount, int time, double price) {
    Double cost = amount * price;
    String query = "SELECT trans FROM History " + "WHERE portfolio = ? "
        + "AND stock = ?;";
    Integer total = 0;
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        prep.setString(2, ticker);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String transString = rs.getString(1);
            Integer trans = Integer.parseInt(transString);
            total += trans;
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      if (total + amount < 0) {
        return false;
      } else {
        String stat = "INSERT INTO History VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement prep = c.prepareStatement(stat)) {
          prep.setString(1, portId);

          prep.setString(2, ticker);

          prep.setInt(3, time);

          prep.setInt(4, amount);

          prep.setDouble(5, price);

          prep.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        stat = "UPDATE Balances " + "SET balance = (balance + ?) "
            + "WHERE portfolio = ?;";
        try (PreparedStatement prep = c.prepareStatement(stat)) {
          prep.setDouble(1, -cost);
          prep.setString(2, portId);
          prep.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  public static Map<String, Integer> getStocksFromPortfolio(String portId) {
    ListMultimap<String, Integer> transactionHistory = ArrayListMultimap
        .create();
    String query = "SELECT stock, trans FROM History " + "WHERE portfolio = ?;";

    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            String transString = rs.getString(2);
            Integer trans = Integer.parseInt(transString);
            transactionHistory.put(ticker, trans);
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

    Map<String, Integer> ret = new HashMap<>();
    for (String ticker : transactionHistory.keySet()) {
      Integer total = 0;
      for (Integer t : transactionHistory.get(ticker)) {
        total += t;
      }
      ret.put(ticker, total);
    }
    return ret;
  }

  public static Double getBalanceFromPortfolio(String portId) {
    System.out.println("port id " + portId);
    Double balance = 0.0;
    String query = "SELECT balance FROM Balances " + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String balanceString = rs.getString(1);
            balance = Double.parseDouble(balanceString);
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
    return balance;
  }

  public static Price getPrice(String ticker, int time) {
    return getPriceHelp(ticker, time, 0);
  }

  private static Price getPriceHelp(String ticker, int time, int calls) {
    if (calls == 0 || calls == 1) {
      System.out.println("get price called in DatabaseAPI");
      System.out.println("ticker: " + ticker + ", time: " + time);
      List<Price> prices = new ArrayList<>();
      String query = "SELECT time, price FROM quotes " + "WHERE stock = ? "
          + "AND time <= ? " + "AND time >= ?;";
      try (Connection c = DriverManager.getConnection(quoteUrl)) {
        Statement s = c.createStatement();
        s.executeUpdate("PRAGMA foreign_keys = ON;");
        try (PreparedStatement prep = c.prepareStatement(query)) {
          prep.setString(1, ticker);
          prep.setInt(2, time + 43200);
          prep.setInt(3, time - 604800);
          try (ResultSet rs = prep.executeQuery()) {
            while (rs.next()) {
              System.out.println("flag");
              Integer timestamp = Integer.parseInt(rs.getString(1));
              Double priceValue = Double.parseDouble(rs.getString(2));
              Price price = new Price(priceValue, (long) timestamp);
              prices.add(price);
            }
            Collections.sort(prices, new Comparator<Price>() {
              @Override
              public int compare(Price o1, Price o2) {
                return o2.getTime().compareTo(o1.getTime());
              }
            });
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      if (prices.size() == 0) {
        // Stock not found
        System.out.println("init f1");
        if (initializePrices(ticker).size() == 0) {
          return null;
        } else {
          return getPriceHelp(ticker, time, calls + 1);
        }
      } else {
        return prices.get(0);
      }
    } else {
      return null;
    }
  }

  public static List<Price> getPrices(String ticker) {
    String query = "SELECT time, price FROM quotes " + "WHERE stock = ?;";
    List<Price> prices = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(quoteUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, ticker);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String time = rs.getString(1);
            String priceValue = rs.getString(2);
            Price price = new Price(Double.valueOf(priceValue),
                Long.valueOf(time));

            prices.add(price);
          }
          Collections.sort(prices, new Comparator<Price>() {
            @Override
            public int compare(Price o1, Price o2) {
              return o2.getTime().compareTo(o1.getTime());
            }
          });
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Collections.sort(prices);
    // if (isUpdated(ticker)) {
    // return prices;
    // }
    // Legacy code above
    // If there are no prices than update the table with update prices.
    if (prices.size() == 0) {
      System.out.println("init f2");
      return initializePrices(ticker);
    } else {
      return prices;
    }
  }

  // public static boolean isUpdated(String ticker) {
  // String query = "SELECT time FROM quotes " + "WHERE stock = ?;";
  // List<Integer> timestamps = new ArrayList<>();
  // try (Connection c = DriverManager.getConnection(quoteUrl)) {
  // Statement s = c.createStatement();
  // s.executeUpdate("PRAGMA foreign_keys = ON;");
  // try (PreparedStatement prep = c.prepareStatement(query)) {
  // prep.setString(1, ticker);
  // try (ResultSet rs = prep.executeQuery()) {
  // while (rs.next()) {
  // Integer time = Integer.parseInt(rs.getString(1));
  // timestamps.add(time);
  // }
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // if (timestamps.size() == 0) {
  // return false;
  // } else {
  // Collections.sort(timestamps);
  // Calendar c = Calendar.getInstance();
  // int day = c.get(Calendar.DAY_OF_WEEK);
  // int timeCheck;
  // switch (day) {
  // case Calendar.SUNDAY:
  //// three days in seconds
  // timeCheck = 259200;
  // break;
  // case Calendar.MONDAY:
  //// four days in seconds
  // timeCheck = 345600;
  // break;
  // default:
  //// two days in seconds
  // timeCheck = 172800;
  // break;
  // }
  // int currTime = (int) (System.currentTimeMillis() / 1000L);
  // int lastNineThirtyAM =
  // currTime - (currTime % 48600);
  // if (currTime - timestamps.get(timestamps.size() - 1) < timeCheck) {
  // return true;
  // } else {
  // return false;
  // }
  // }
  // }

  public static List<Price> initializePrices(String ticker) {
    System.out.println("initialize prices called");
    // String stat = "DELETE FROM quotes WHERE stock = ?;";
    // try (Connection c = DriverManager.getConnection(quoteUrl)) {
    // Statement s = c.createStatement();
    // s.executeUpdate("PRAGMA foreign_keys = ON;");
    // try (PreparedStatement prep = c.prepareStatement(stat)) {
    // prep.setString(1, ticker);
    // prep.executeUpdate();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    List<Price> ret = new ArrayList<>();
    try {
      Calendar from = Calendar.getInstance();
      from.add(Calendar.YEAR, -10);
      yahoofinance.Stock stock = YahooFinance.get(ticker);
      // Gets daily data from the past ten years
      List<HistoricalQuote> quotes = stock.getHistory(from, Interval.DAILY);
      String stat = "INSERT INTO quotes VALUES (?, ?, ?);";
      try (Connection c = DriverManager.getConnection(quoteUrl)) {
        Statement s = c.createStatement();
        s.executeUpdate("PRAGMA foreign_keys = ON;");
        try (PreparedStatement prep = c.prepareStatement(stat)) {
          for (HistoricalQuote q : quotes) {
            Double priceVal = q.getAdjClose().doubleValue();
            // Shifts a midnight time 16 hours to the 4 pm close time
            Long time = (q.getDate().getTimeInMillis() / 1000) + 57600;
            Price p = new Price(priceVal, time);
            ret.add(p);
            prep.setString(1, ticker);
            prep.setString(2, time.toString());
            prep.setString(3, priceVal.toString());
            prep.addBatch();
          }
          prep.executeBatch();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      // Not found
    }
    return ret;
  }

  public static boolean initializePool(Pool p) {
    System.out.println("initialze pool calledz");
    String stat = "INSERT INTO Pools " + "VALUES (?, ?, ?, ?, ?);";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setLong(5, p.getEnd());
        prep.setLong(4, p.getStart());
        prep.setInt(3, p.getBal());
        prep.setString(2, p.getName());
        prep.setString(1, p.getId());
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static Pool getPool(String poolId) {
    String query = "SELECT * FROM Pools WHERE PoolId = ?";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, poolId);
        try (ResultSet rs = prep.executeQuery()) {
          if (rs.next()) {
            System.out.println("klip klop");
            String name = rs.getString(2);
            int bal = Integer.parseInt(rs.getString(3));
            long start = rs.getLong(4);
            long end = rs.getLong(5);
            Pool pool = new Pool(poolId, name, bal, start, end);
            return pool;
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
    return null;
  }

  public static List<Portfolio> getPortsFromPool(String pool) {
    String query = "SELECT Name, PortfolioId, UserId FROM UserPortfolios "
        + "WHERE PoolId = ?;";
    List<Portfolio> portfolios = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, pool);
        try (ResultSet rs = prep.executeQuery()) {
          Pool p = getPool(pool);
          while (rs.next()) {
            String name = rs.getString(1);
            String id = rs.getString(2);
            String user = rs.getString(3);
            Portfolio port = new Portfolio(name, id);
            port.setPool(p);
            port.setUser(user);
            portfolios.add(port);
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
    System.out.println(portfolios);
    return portfolios;
  }

  public static boolean addPortToPool(String portId, String poolId) {
    String query = "UPDATE UserPortfolios SET PoolId = ? WHERE PortfolioId = "
        + "?";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, poolId);
        prep.setString(2, portId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static List<Portfolio> getPoolsFromUser(String userId) {
    String query = "SELECT * FROM UserPortfolios "
        + "WHERE UserId = ? AND PoolId IS NOT NULL;";
    List<Portfolio> ports = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, userId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String pool = rs.getString(4);
            Pool p = getPool(pool);
            String id = rs.getString(1);
            String name = rs.getString(2);
            Portfolio port = new Portfolio(name, id);
            port.setPool(p);
            ports.add(port);
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
    return ports;
  }

  public static boolean endPools() {
    String stat = "UPDATE Pools SET Active = 'FALSE' "
        + "WHERE Active IS TRUE AND End < ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setLong(1, System.currentTimeMillis() / 1000);
        prep.executeUpdate();
        return true;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static Pool getPoolFromPortfolio(String portId) {
    Pool pool;
    String query = "SELECT PoolId FROM UserPortfolios "
        + "WHERE UserId = ? AND PoolId IS NOT NULL;";
    String poolId = "";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            poolId = rs.getString(1);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      query = "SELECT Name, Balance, Start, End FROM POOLS "
          + "WHERE PoolId = ?;";
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, poolId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String poolName = rs.getString(1);
            Integer initBalance = Integer.parseInt(rs.getString(2));
            long startTime = rs.getLong(3);
            long endTime = rs.getLong(4);
            pool = new Pool(poolId, poolName, initBalance, startTime, endTime);
            return pool;
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
    return null;
  }
}
