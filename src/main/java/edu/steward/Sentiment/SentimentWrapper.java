package edu.steward.Sentiment;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by Philip on 5/1/17.
 */
public class SentimentWrapper {
  public static void main(String[] args) {
    TwitterSentimentFinder tsf = new TwitterSentimentFinder();
    System.out.println(sentimentNormalizer(tsf.sentiments(ImmutableList.of
            ("AMZN")).get("AMZN")));
  }

  private static double sentimentNormalizer(List<Integer> sentiments) {
    int total = sentiments.size();
    int pos = 0;
    int neu = 0;
    int neg = 0;

    for (Integer i : sentiments) {
      switch (i) {
        case 1: neg++; break;
        case 2: neu++; break;
        case 3: pos++; break;
        default: break;
      }
    }

    System.out.println("Total:" + total);
    System.out.println("Positives: " + pos);
    System.out.println("Negatives: " + neg);
    System.out.println("Neutrals : " + neu);

    return 0.0;
  }
}
