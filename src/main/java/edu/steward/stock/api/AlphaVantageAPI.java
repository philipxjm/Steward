package edu.steward.stock.api;

import com.google.common.collect.Lists;
import edu.steward.main.JSONRetriever;
import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by mrobins on 4/17/17.
 */
public class AlphaVantageAPI implements StockAPI {
  private static final Gson GSON = new Gson();

  @Override
  public List<Price> getStockPrices(String ticker, TIMESERIES timeSeries) {

    if (ticker != null) {
      Map<String, Map<String, Double>> timeSeriesData = new HashMap<>();
      String rawData;
      switch (timeSeries) {
        case ONE_DAY:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_INTRADAY,
                  AlphaVantageConstants.INTERVAL.FIVE_MIN,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.INTERVAL.FIVE_MIN);
          break;
        case FIVE_DAY:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_INTRADAY,
                  AlphaVantageConstants.INTERVAL.THIRTY_MIN,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.INTERVAL.THIRTY_MIN);
          break;
        case ONE_MONTH:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY,
                  AlphaVantageConstants.OUTPUT_SIZE.COMPACT,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY);
          break;
        case SIX_MONTH:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY);
          break;
        case ONE_YEAR:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY);
          break;
        case TWO_YEAR:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY);
          break;
        case FIVE_YEAR:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY);
          break;
        case TEN_YEAR:
          rawData = getFromAlphaVantage(
                  ticker,
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_MONTHLY,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_MONTHLY);
          break;
      }
      List<Price> prices = parseTimeSeriesMap(timeSeriesData);
      return priceIntervalClean(prices, timeSeries);
    } else {
      System.out.println("something went wrong in get stock prices.");
      return null;
    }
  }

  public String getFromAlphaVantage(String ticker, Enum... args) {
    String url = constructURL(ticker, args);
    System.out.println("url is: " + url);
    return JSONRetriever.getJSON(url, 5000);
  }

  public Map<String, Map<String, Double>> parseAlphaVantage(
          String json,
          Enum timeSeries
  ) {
    String time;
    switch (timeSeries.name()) {
      case "ONE_MIN":
        time = "Time Series (1min)";
        break;
      case "FIVE_MIN":
        time = "Time Series (5min)";
        break;
      case "FIFTEEN_MIN":
        time = "Time Series (15min)";
        break;
      case "THIRTY_MIN":
        time = "Time Series (30min)";
        break;
      case "SIXTY_MIN":
        time = "Time Series (60min)";
        break;
      case "TIME_SERIES_DAILY":
        time = "Time Series (Daily)";
        break;
      case "TIME_SERIES_WEEKLY":
        time = "Weekly Time Series";
        break;
      case "TIME_SERIES_MONTHLY":
        time = "Monthly Time Series";
        break;
      default:
        time = "";
    }

    JsonObject received = GSON.fromJson(json, JsonObject.class);
    JsonObject meta = received.get("Meta Data").getAsJsonObject();

    // Meta
    String info;
    String sym;
    String last;
    String inter;
    String output;
    String timezone;

    try {
      info = meta.get("1. Information").getAsString();
    } catch (NullPointerException e) {
//      ignore
    }
    try {
      sym = meta.get("2. Symbol").getAsString();
    } catch (NullPointerException e) {
//      ignore
    }
    try {
      last = meta.get("3. Last Refreshed").getAsString();
    } catch (NullPointerException e) {
//      ignore
    }
    try {
      inter = meta.get("4. Interval").getAsString();
    } catch (NullPointerException e) {
//      ignore
    }
    try {
      output = meta.get("5. Output Size").getAsString();
    } catch (NullPointerException e) {
//      ignore
    }
    try {
      timezone = meta.get("6. Time Zone").getAsString();
    } catch (NullPointerException e) {
//      ignore
    }

    // Timeseries
    JsonObject timeseries = received.get(time).getAsJsonObject();
    Map<String, Map<String, Double>> ret = new HashMap<>();
    for (Map.Entry<String, JsonElement> e : timeseries.entrySet()) {
      Map<String, Double> toAdd = new HashMap<>();
      JsonObject obj = (JsonObject) e.getValue();
      toAdd.put("open", obj.get("1. open").getAsDouble());
      toAdd.put("high", obj.get("2. high").getAsDouble());
      toAdd.put("low", obj.get("3. low").getAsDouble());
      toAdd.put("close", obj.get("4. close").getAsDouble());
      toAdd.put("volume", obj.get("5. volume").getAsDouble());
      ret.put(e.getKey(), toAdd);
    }
    return ret;
  }

  private String constructURL(String ticker, Enum... args) {
    String url = "";
    try {
      url = "http://www.alphavantage.co/query?function=";
      for (Enum e : args) {
        switch (getType(e)) {
          case ("FUNCTION"):
            url +=
                    URLEncoder.encode(
                            e.toString(),
                            "UTF-8");
            url += "&symbol=" + ticker;
            break;
          case ("INTERVAL"):
            url += "&interval=";
            url += URLEncoder.encode(e.toString(), "UTF-8");
            break;
          case ("OUTPUT_SIZE"):
            url += "&outputsize=";
            url += URLEncoder.encode(e.toString(), "UTF-8");
            break;
          case ("APIKEY"):
            url += "&apikey=";
            url += URLEncoder.encode(e.toString(), "UTF-8");
        }
      }
    } catch (UnsupportedEncodingException e) {
      System.out.println("Error with API Call URL");
    }
    return url;
  }

  private static String getType(Enum e) {
    String[] type = e.getClass().getName().split("\\$");
    return type[1];
  }

  private static List<Price> parseTimeSeriesMap(
          Map<String, Map<String, Double>> timeSeriesData) {
    List<Price> ret = new ArrayList<>();
    for (String timeStamp : timeSeriesData.keySet()) {
      Long time = getTime(timeStamp);
      double priceVal = timeSeriesData.get(timeStamp).get("close");
      Price p = new Price(priceVal, time);
      ret.add(p);
    }
    Collections.sort(ret, new Comparator<Price>() {
      @Override
      public int compare(Price o1, Price o2) {
        return o2.getTime().compareTo(o1.getTime());
      }
    });
    return ret;
  }

  private static long getTime(String timeStamp) {
    DateTimeFormatter formatter1 =
            DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTimeFormatter formatter2 =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTime dateTime = new DateTime();
    try {
      dateTime = formatter1.parseDateTime(timeStamp);
    } catch (IllegalArgumentException e1) {
//      try other format
      try {
        dateTime = formatter2.parseDateTime(timeStamp);
      } catch (IllegalArgumentException e2) {
        e2.printStackTrace();
      }
    }
    if (dateTime == new DateTime()) {

    }
    long unixTime = dateTime.getMillis() / 1000;
    return unixTime;
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker) {
    return null;
  }

  @Override
  public Price getCurrPrice(String ticker) {
    return null;
  }

  @Override
  public DailyChange getDailyChange(String ticker) {
    return null;
  }

  private List<Price> priceIntervalClean(List<Price> prices, TIMESERIES t) {
    List<Price> ret = new ArrayList<>();
    Long cutoffTime;
    switch (t) {
      case ONE_DAY:
        cutoffTime = prices.get(0).getTime() - (prices.get(0).getTime() % 34200);
        for (Price p : prices) {
          if (p.getTime() >= cutoffTime) {
            ret.add(p);
          } else {
            break;
          }
        }
        break;
      case FIVE_DAY:
        int counter = 0;
//        Gets the start of the day
        cutoffTime = prices.get(0).getTime() - (prices.get(0).getTime() % 34200);
//        Counts where the last day begins
        for (Price p : prices) {
          if (p.getTime() >= cutoffTime) {
            counter++;
            ret.add(p);
          }
        }
        for (int i = counter; i < counter + 56; i++) {
          ret.add(prices.get(i));
        }
        break;
      case ONE_MONTH:
        for (int i = 0; i < 24; i++) {
          System.out.println("yeee:" + i);
          ret.add(prices.get(i));
        }
        break;
      case SIX_MONTH:
        for (int i = 0; i < 26; i++) {
          ret.add(prices.get(i));
        }
        break;
      case ONE_YEAR:
        for (int i = 0; i < 52; i++) {
          ret.add(prices.get(i));
        }
        break;
      case TWO_YEAR:
        for (int i = 0; i < 104; i++) {
          System.out.println(i + ": " + prices.get(i).getTime());
          System.out.println(i + ": " + prices.get(i).getValue());

          ret.add(prices.get(i));
        }
        break;
      case FIVE_YEAR:
        for (int i = 0; i < 260; i++) {
          ret.add(prices.get(i));
        }
        break;
      case TEN_YEAR:
        for (int i = 0; i < 120; i++) {
          ret.add(prices.get(i));
        }
        break;
    }
    return ret;
  }
}
