package edu.steward.Sentiment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Philip on 5/3/17.
 */
public class SentimentCache {
  private static Map<String, TimeSentiment> cache = new HashMap<>();

  public static void insertToCache(String ticker, double sentiment) {
    cache.put(ticker,
            new TimeSentiment(System.currentTimeMillis() / 1000L, sentiment));
  }

  public static double getFromCache(String ticker) {
    return cache.get(ticker).getSentiment();
  }

  public static boolean doesContainUpToDate(String ticker) {
    return cache.containsKey(ticker) && (Math.abs(cache.get(ticker).getTime() -
            (System.currentTimeMillis() / 1000L)) < 30000);
  }

  public static String cacheString() {
    return cache.toString();
  }
}

class TimeSentiment {
  private long time;
  private double sentiment;

  public TimeSentiment(long time, double sentiment) {
    this.time = time;
    this.sentiment = sentiment;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public double getSentiment() {
    return sentiment;
  }

  public void setSentiment(double sentiment) {
    this.sentiment = sentiment;
  }

  @Override
  public String toString() {
    return "{time: " + time + ", sentiment: " + sentiment + "}";
  }
}