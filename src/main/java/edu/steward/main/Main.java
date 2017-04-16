package edu.steward.main;

import edu.steward.analytics.SentimentAnalysis;

/**
 * Hello world!
 */
public class Main {
  public static void main(String[] args) {
//    System.out.println(TwitterSearch.search("Trump"));
    System.out.println(SentimentAnalysis.findSentiment("AAPL is " +
            "exceptionally profitable" +
            "."));
  }
}
