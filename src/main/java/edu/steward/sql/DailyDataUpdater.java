package edu.steward.sql;

import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.Stock;
import edu.steward.stock.api.AlphaVantageAPI;
import edu.steward.stock.api.StockAPI;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by mrobins on 5/3/17.
 */
public class DailyDataUpdater {

  enum INTERVAL {
    FIVE_MIN,
    THIRTY_MIN,
    DAILY
  }

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";
  private static String quoteUrl = base + "data/quotes.sqlite3";

  public static List<Price> initializeIfEmptyFive(String ticker) {
    return initializeIfEmptyHelp(ticker, INTERVAL.FIVE_MIN);
  }

  public static List<Price> initializeIfEmptyThirty(String ticker) {
    return initializeIfEmptyHelp(ticker, INTERVAL.THIRTY_MIN);
  }

  //  TODO: Calls AlphaVantage API and adds 30min and 5min daily data to the db
  private static List<Price> initializeIfEmptyHelp(String ticker, INTERVAL interval) {
    List<Price> ret = new ArrayList<>();
    String query1 = "";
    String query2 = "";
    StockAPI.TIMESERIES series = StockAPI.TIMESERIES.FIVE_DAY;
    switch (interval) {
      case FIVE_MIN:
        query1 = "SELECT stock, time, price FROM fiveMin WHERE stock = ?;";
        query2 = "INSERT INTO fiveMin VALUES (?, ?, ?);";
        series = StockAPI.TIMESERIES.ONE_DAY;
        break;
      case THIRTY_MIN:
        query1 = "SELECT stock, time, price FROM thirtyMin WHERE stock = ?;";
        query2 = "INSERT INTO thirtyMin VALUES (?, ?, ?);";
        series = StockAPI.TIMESERIES.FIVE_DAY;
        break;
    }
    try (Connection c = DriverManager.getConnection(quoteUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query1)) {
        prep.setString(1, ticker);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String stock = rs.getString(1);
            Integer time = Integer.parseInt(rs.getString(2));
            Double priceVal = Double.parseDouble(rs.getString(3));
            Price price = new Price(priceVal, (long) time);
            ret.add(price);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
//        Has not been initialized
        if (ret.size() == 0) {
          try (PreparedStatement prep2 = c.prepareStatement(query2)) {

//            Initializes data using the AlphaVantage API
            StockAPI stockAPI = new AlphaVantageAPI();
            List<Price> prices = stockAPI.getStockPrices(ticker, series);

            for (Price p : prices) {
              prep2.setString(1, ticker);
              prep2.setInt(2, p.getTime().intValue());
              prep2.setDouble(3, p.getValue());
              prep2.addBatch();
            }
            prep2.executeBatch();
            return prices;
          } catch (SQLException e) {
            e.printStackTrace();
          }
        } else {
          return ret;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ret;
  }

//  TODO: find usages?? i should get rid of this
  public void update() {
    updateFive();
    updateThirty();
  }

  //  TODO: Make update run every fifth minute (9:30...10:25, 10:30,...4)
  static void updateFive() {
    System.out.println("five min update called");
    updateHelp(INTERVAL.FIVE_MIN);
  }

  //  TODO: Make update run every thirty minutes (9:30...10:00, 10:30,...4)
  static void updateThirty() {
    updateHelp(INTERVAL.THIRTY_MIN);
  }

//  TODO: Make update run at the end of every close day
  static void updateDaily() { updateHelp(INTERVAL.DAILY);}

  private static void updateHelp(INTERVAL interval) {
    String query1 = "";
    String query2 = "";
    switch (interval) {
      case FIVE_MIN:
        query1 = "SELECT stock FROM fiveMin;";
        query2 = "INSERT INTO fiveMin VALUES (?, ?, ?);";
        break;
      case THIRTY_MIN:
        query1 = "SELECT stock FROM thirtyMin;";
        query2 = "INSERT INTO thirtyMin VALUES (?, ?, ?);";
        break;
      case DAILY:
        query1 = "SELECT stock FROM quotes;";
        query2 = "INSERT INTO quotes VALUES (?, ?, ?);";
    }
    Integer timeCalled = (int) (System.currentTimeMillis() / 1000L);
    Set<String> tickers = new HashSet<>();
    try (Connection c = DriverManager.getConnection(quoteUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query1)) {
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String ticker = rs.getString(1);
            tickers.add(ticker);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try (PreparedStatement prep = c.prepareStatement(query2)) {
        for (String ticker : tickers) {
          prep.setString(1, ticker);
          prep.setInt(2, timeCalled);

          Stock stock = new Stock(ticker);
          Double priceValue = stock.getCurrPrice().getValue();
          prep.setDouble(3, priceValue);
          prep.addBatch();
        }
        prep.executeBatch();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
