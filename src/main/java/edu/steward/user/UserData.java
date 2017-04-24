package edu.steward.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kjin on 4/24/17.
 */
public class UserData {

  private static String dbloc = "data/users.sqlite3";
  private static String url = "jdbc:sqlite:" + dbloc;

  public static List<Portfolio> getPortfoliosFromUser(String userId) {
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
}
