package edu.steward.sql;

import edu.steward.ai.traders.Trader;
import edu.steward.user.Portfolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 5/5/17.
 */
public final class aiRetriever {

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";

  public static List<Trader> getActiveAIs() {
    String query = "SELECT PortfolioId FROM UserPortfolios; "
            + "WHERE (PortfolioId like ?);";
    List<Trader> portfolios = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, "ai");
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String name = rs.getString(1);
            String id = rs.getString(2);
            String user = rs.getString(3);
            Trader port = new Trader(name, id);
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
    return portfolios;
  }
}
