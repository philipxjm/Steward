package edu.steward.main;

import com.google.common.collect.ImmutableList;
import edu.steward.analytics.SentimentAnalysis;
import edu.steward.analytics.TwitterSentiments;

/**
 * Hello world!
 */
public class Main {
  public static void main(String[] args) {
//    System.out.println(TwitterSearch.search("Trump"));
    System.out.println(TwitterSentiments.sentiments(ImmutableList.of
            ("Kendrick")));
  }
}
