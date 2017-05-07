package edu.steward.sql;

import edu.steward.pools.Pool;
import edu.steward.user.LBscore;
import edu.steward.user.Portfolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrobins on 5/7/17.
 */
public class LeaderBoard {

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";

  public static List<LBscore> getLeaderBoard(String poolId) {
    List<LBscore> ret = new ArrayList<>();
    String query = "SELECT portfolio, score FROM Leaderboards "
            + "WHERE pool = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, poolId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String portId = rs.getString(1);
            Double score = rs.getDouble(2);
            ret.add(new LBscore(new Portfolio("", portId), score));
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

  public static boolean containsPortfolio(String portId) {
    System.out.println("checked if leaderboard contains portfolio");
    String query = "SELECT * FROM Leaderboards "
            + "WHERE portfolio = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, portId);
        try (ResultSet rs = prep.executeQuery()) {
//          returns whether or not the portfolio is finished and in the leaderboards
          return rs.next();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

}
