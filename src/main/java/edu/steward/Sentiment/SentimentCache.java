package edu.steward.Sentiment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Philip on 5/3/17.
 */
public class SentimentCache {
  private static Map<String, Double> cache = new HashMap<>();

  public static void insertToCache(String ticker, double sentiment) {
    cache.put(ticker, sentiment);
  }

  public static double getFromCache(String ticker) {
    return cache.get(ticker);
  }

  public static boolean doesContain(String ticker) {
    return cache.containsKey(ticker);
  }
}
