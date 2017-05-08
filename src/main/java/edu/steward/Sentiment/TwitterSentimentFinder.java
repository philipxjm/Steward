package edu.steward.Sentiment;

import com.google.common.collect.ImmutableList;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Philip on 4/16/17.
 */
public class TwitterSentimentFinder {
  private SentimentAnalyzer sa;
//  private TwitterSearcher ts;
  private StockTwitSearcher ts;

  public TwitterSentimentFinder() {
    this.sa = new SentimentAnalyzer();
//    this.ts = new TwitterSearcher();
    this.ts = new StockTwitSearcher();
  }

  public Map<String, List<Integer>> sentiments(List<String> keywords) {
    Set<String> keywordSet = new HashSet<String>(keywords);
    Map<String, List<Integer>> results = new HashMap<String, List<Integer>>();
    if (keywordSet.size() == 0) {
      return results;
    }
    for (String keyword : keywordSet) {
//      List<Status> statuses = ts.search(keyword);
      List<String> statuses = ts.search(keyword);
      System.out.println("Performing sentimental analysis on " + keyword +
              ". Found " +
              "statuses " +
              "... " + statuses.size());
      List<Integer> sentiments = new ArrayList<Integer>();
      for (String status : statuses) {
        Integer sen = sa.findSentiment(status);
        if (sen != -1) {
          sentiments.add(sen);
        }
      }
      results.put(keyword, sentiments);
    }
    return results;
  }
}
