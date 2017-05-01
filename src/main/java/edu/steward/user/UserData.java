package edu.steward.user;

import com.google.common.collect.*;

import java.sql.*;
import java.util.*;

/**
 * Created by kjin on 4/24/17.
 */
public class UserData {

  private static String dbloc = "data/users.sqlite3";
  private static String url = "jdbc:sqlite:" + dbloc;

  public static List<Portfolio> getPortfoliosFromUser(String userId) {
    System.out.println("get port from user called");
    String query = "SELECT Name, PortfolioId FROM UserPortfolios "
        + "WHERE UserId = ?;";
    List<Portfolio> portfolios = new ArrayList<>();
    System.out.println("id: " + userId);
    try (Connection c = DriverManager.getConnection(url)) {
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

  public static void createPortfolio(String userId, String portName, Integer initialBalance) {
    String stat = "INSERT INTO UserPortfolios VALUES (?, ?, ?);";
    try (Connection c = DriverManager.getConnection(url)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setString(3, userId);
        prep.setString(2, portName);
        String portId = userId + "/" + portName;
        prep.setString(1, portId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      stat = "INSERT INTO Balances VALUES (?, ?)";
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setString(1, userId + "/" + portName);
        prep.setInt(2, initialBalance);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void createPortfolio(String userId, String portName) {
    String stat = "INSERT INTO UserPortfolios VALUES (?, ?, ?);";
    try (Connection c = DriverManager.getConnection(url)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setString(3, userId);
        prep.setString(2, portName);
        String portId = userId + "/" + portName;
        prep.setString(1, portId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      stat = "INSERT INTO Balances VALUES (?, ?)";
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        prep.setString(1, userId + "/" + portName);
        prep.setInt(2, 1000000);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void removePortfolio(String userId, String portName) {
    String stat = "DELETE FROM UserPortfolios WHERE PortfolioId = ?;";
    try (Connection c = DriverManager.getConnection(url)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(stat)) {
        String portId = userId + "/" + portName;
        prep.setString(1, portId);
        prep.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static boolean stockTransaction(
          String portId,
          String ticker,
          int amount,
          int time,
          double price) {
    System.out.println("abcddbca");
    Double cost = amount * price;
    String query = "SELECT time, quantity FROM History "
            + "WHERE portfolio = ? "
            + "AND stock = ?;";
    Integer lastQuantity = 0;
    try (Connection c = DriverManager.getConnection(url)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        System.out.println("portId: " + portId);
        prep.setString(2, ticker);
        System.out.println("ticker: " + ticker);
        try (ResultSet rs = prep.executeQuery()) {
          int recentTime = 0;
          while (rs.next()) {
            System.out.println("printed dis123");
            String timeString = rs.getString(1);
            System.out.println("time string: " + time);
            Integer timeStamp = Integer.parseInt(timeString);
            System.out.println("timeStamp " + timeStamp);

            String quantityString = rs.getString(2);
            System.out.println("qString: " + quantityString);
            Integer quantity = Integer.parseInt(quantityString);
            System.out.println("q: " + quantity);

            if (timeStamp > recentTime) {
              recentTime = timeStamp;
              lastQuantity = quantity;
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
    if (lastQuantity + amount < 0) {
      return false;
    } else {
      String stat = "INSERT INTO History VALUES (?, ?, ?, ?, ?, ?);";
      try (Connection c = DriverManager.getConnection(url)) {
        Statement s = c.createStatement();
        s.executeUpdate("PRAGMA foreign_keys = ON;");
        try (PreparedStatement prep = c.prepareStatement(stat)) {

          System.out.println("made it in hghkj");

          prep.setString(1, portId);

          prep.setString(2, ticker);

          prep.setInt(3, time);

          prep.setInt(4, amount);

          prep.setInt(5, lastQuantity + amount);

          prep.setDouble(6, price);

          prep.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        stat = "UPDATE Balances "
                + "SET balance = (balance + ?) "
                + "WHERE portfolio = ?;";
        try (PreparedStatement prep = c.prepareStatement(stat)) {
          System.out.println("lkjlkj");
          prep.setDouble(1, -cost);
          prep.setString(2, portId);
          prep.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return true;
    }
  }

  public static Map<String, Integer> getStocksFromPortfolio(String portId) {
    SortedSetMultimap<String, Holding> transactionHistory = TreeMultimap.create();
    String query = "SELECT stock, time, quantity FROM History "
            + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(url)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            String timeString = rs.getString(2);
            String quantityString = rs.getString(3);
            Holding holding = new Holding(
                    ticker,
                    Integer.parseInt(quantityString),
                    Integer.parseInt(timeString)
                    );
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
    Map<String, Integer> ret = new HashMap<>();
    for (String ticker : transactionHistory.keySet()) {
      ret.put(ticker, transactionHistory.get(ticker).last().getShares());
    }
    return ret;
  }

  public static Double getBalanceFromPortfolio(String portId) {
    Double balance = 0.0;
    String query = "SELECT balance FROM Balances "
            + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(url)) {
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
}
