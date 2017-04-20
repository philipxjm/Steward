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
  private TwitterSearcher ts;

  public TwitterSentimentFinder() {
    this.sa = new SentimentAnalyzer();
    this.ts = new TwitterSearcher();
  }

  public Map<String, List<Integer>> sentiments(List<String> keywords) {
    Set<String> keywordSet = new HashSet<String>(keywords);
    Map<String, List<Integer>> results = new HashMap<String, List<Integer>>();
    if (keywordSet.size() == 0) {
      return results;
    }
    for (String keyword : keywordSet) {
      List<Status> statuses = ts.search(keyword);
      System.out.println("Found statuses ... " + statuses.size());
      List<Integer> sentiments = new ArrayList<Integer>();
      for (Status status : statuses) {
        Integer sen = sa.findSentiment(status.getText());
        if (sen != -1) {
          sentiments.add(sen);
        }
      }
      results.put(keyword, sentiments);
    }
    return results;
  }

  public static void main(String[] args) {
    TwitterSentimentFinder tsf = new TwitterSentimentFinder();
    System.out.println(tsf.sentiments(ImmutableList.of("AMD")));
  }
}
