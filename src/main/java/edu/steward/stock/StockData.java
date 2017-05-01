package edu.steward.stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import edu.steward.stock.Fundamentals.Price;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by mrobins on 4/25/17.
 */
public final class StockData {

  private static String dbloc = "data/quotes.sqlite3";
  private static String url = "jdbc:sqlite:" + dbloc;

  public static List<Price> getPrices(String ticker) {
    System.out.println("made it in get prices");
    System.out.println("ticker: " + ticker);
    String query = "SELECT time, price FROM quotes " + "WHERE stock = ?;";
    List<Price> prices = new ArrayList<>();
    try (Connection c = DriverManager.getConnection(url)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        prep.setString(1, ticker);
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            System.out.println("heia");
            String time = rs.getString(1);
            String priceValue = rs.getString(2);
            System.out.println("priceval: " + priceValue);
            System.out.println("time: " + time);
            Price price = new Price(Double.valueOf(priceValue),
                Long.valueOf(time));
            System.out.println(price.getTime());
            System.out.println(price.getValue());
            prices.add(price);
          }
          Collections.sort(prices, new Comparator<Price>() {
            @Override
            public int compare(Price o1, Price o2) {
              return o1.getTime().compareTo(o2.getTime());
            }
          });
        }
      } catch (SQLException e) {
        System.out.println("flag 1");
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("flag 2");
    }
    if (prices.size() == 0) {
      updatePrices(ticker);
      return getPrices(ticker);
    } else {
      Collections.sort(prices);
      if ((System.currentTimeMillis() / 1000)
          - prices.get(0).getTime() < 777600) {
        System.out.println("twas returned");
        return prices;
      } else {
        System.out.println("hasnt been updated in nine days so it is updating");
        System.out.println("priceztyme: " + prices.get(0).getTime());
        System.out.println("lastpriceztyme: " + prices.get(prices.size() - 1).getTime());
        System.out.println("currtyme: " + (System.currentTimeMillis() / 1000));
        updatePrices(ticker);
        return getPrices(ticker);
      }
    }
  }

  public static void updatePrices(String ticker) {
    System.out.println("update prices called");
    List<Price> ret = new ArrayList<>();
    try {
      Calendar from = Calendar.getInstance();
      from.add(Calendar.YEAR, -10);
      yahoofinance.Stock stock = YahooFinance.get(ticker);
      List<HistoricalQuote> quotes = stock.getHistory(from, Interval.WEEKLY);
      for (HistoricalQuote q : quotes) {
        System.out.println("adddddddded");
        Double priceVal = q.getAdjClose().doubleValue();
        Long time = q.getDate().getTimeInMillis() / 1000;
        Price p = new Price(priceVal, time);
        ret.add(p);
        String stat = "INSERT OR REPLACE INTO quotes VALUES (?, ?, ?);";
        try (Connection c = DriverManager.getConnection(url)) {
          Statement s = c.createStatement();
          s.executeUpdate("PRAGMA foreign_keys = ON;");
          try (PreparedStatement prep = c.prepareStatement(stat)) {
            prep.setString(1, ticker);
            prep.setString(2, time.toString());
            prep.setString(3, priceVal.toString());
            prep.executeUpdate();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      // Not found
    }
  }
}
