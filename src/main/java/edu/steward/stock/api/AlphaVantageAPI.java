package edu.steward.stock.api;

import edu.steward.main.JSONRetriever;
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

    Enum sym = null;
    for (Enum symbol : AlphaVantageConstants.SYMBOL.values()) {
      if (ticker.equals(symbol.name())) {
        sym = symbol;
        break;
      }
    }

    if (sym != null) {
      Map<String, Map<String, Double>> timeSeriesData = new HashMap<>();
      String rawData;
      switch (timeSeries) {
        case ONE_DAY:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_INTRADAY,
                  sym,
                  AlphaVantageConstants.INTERVAL.ONE_MIN,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.INTERVAL.ONE_MIN);
          break;
        case FIVE_DAY:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_INTRADAY,
                  sym,
                  AlphaVantageConstants.INTERVAL.FIFTEEN_MIN,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.INTERVAL.FIFTEEN_MIN);
          break;
        case ONE_MONTH:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY,
                  sym,
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
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY,
                  sym,
                  AlphaVantageConstants.OUTPUT_SIZE.FULL,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY);
          break;
        case ONE_YEAR:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY,
                  sym,
                  AlphaVantageConstants.OUTPUT_SIZE.FULL,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY);
          break;
        case TWO_YEAR:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY,
                  sym,
                  AlphaVantageConstants.OUTPUT_SIZE.FULL,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_DAILY);
          break;
        case FIVE_YEAR:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY,
                  sym,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_WEEKLY);
          break;
        case TEN_YEAR:
          rawData = getFromAlphaVantage(
                  AlphaVantageConstants.FUNCTION.TIME_SERIES_MONTHLY,
                  sym,
                  AlphaVantageConstants.APIKEY.APIKEY
          );
          timeSeriesData =
                  parseAlphaVantage(
                          rawData,
                          AlphaVantageConstants.FUNCTION.TIME_SERIES_MONTHLY);
          break;
      }
      List<Price> prices = parseTimeSeriesMap(timeSeriesData);
      return prices;
    } else {
      System.out.println("something went wrong in get stock prices.");
      return null;
    }
  }

  public String getFromAlphaVantage(Enum... args) {
    String url = constructURL(args);
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

  private String constructURL(Enum... args) {
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
            break;
          case ("SYMBOL"):
            url += "&symbol=";
            url += URLEncoder.encode(e.toString(), "UTF-8");
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
        return o1.getTime().compareTo(o2.getTime());
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
}
