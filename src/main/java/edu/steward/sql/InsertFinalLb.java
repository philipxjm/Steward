package edu.steward.sql;

import com.google.common.collect.ImmutableMap;
import edu.steward.pools.Pool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

/**
 * Created by mrobins on 5/7/17.
 */
public class InsertFinalLb {

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";

  public static void insert(String poolId) {
    System.out.println("insert called");
    System.out.println(poolId);
//    TODO: get every portfolio from pool, get current Net worth for each, cache in leaderboard db.
//    TODO: Add to lb data to db!!
    List<String> portfolioIds = new ArrayList<>();
    String query = "SELECT PortfolioId FROM UserPortfolios "
            + "WHERE PoolId  = ?;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, poolId);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            System.out.println("AAAA!");
            String portId = rs.getString(1);
            portfolioIds.add(portId);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
      }
      String update = "INSERT INTO Leaderboards VALUES (?, ?, ?);";
      try (PreparedStatement prep = c.prepareStatement(update)) {
        for (String portId : portfolioIds) {
          Double finalWorth = GainsOverTime.getCurrentNetWorth(portId);
          prep.setString(1, poolId);
          prep.setString(2, portId);
          prep.setDouble(3, finalWorth);
          prep.addBatch();
        }
        prep.executeBatch();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
