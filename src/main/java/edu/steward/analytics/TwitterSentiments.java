package edu.steward.analytics;

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
public final class TwitterSentiments {
  public static Map<String, List<Integer>> sentiments(List<String> keywords) {
    Set<String> keywordSet = new HashSet<String>(keywords);
    Map<String, List<Integer>> results = new HashMap<String, List<Integer>>();
    if (keywordSet.size() == 0) {
      return results;
    }
    for (String keyword : keywordSet) {
      List<Status> statuses = TwitterSearch.search(keyword);
      System.out.println("Found statuses ... " + statuses.size());
      List<Integer> sentiments = new ArrayList<Integer>();
      for (Status status : statuses) {
        Integer sen = SentimentAnalysis.findSentiment(status.getText());
        if (sen != -1) {
          sentiments.add(sen);
        }
      }
      results.put(keyword, sentiments);
    }
    return results;
  }
}
