package edu.steward.Sentiment;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by Philip on 5/1/17.
 */
public class SentimentWrapper {

  private TwitterSentimentFinder tsf;

  public SentimentWrapper() {
    tsf = new TwitterSentimentFinder();
  }

  public double findSentimentOf(String ticker) {
    List<Integer> sentiments
            = tsf.sentiments(ImmutableList.of(ticker)).get(ticker);
    return sentimentNormalizer(ticker, sentiments);
  }

  private double sentimentNormalizer(String ticker, List<Integer> sentiments) {

    double bullish = StockTwitSentimentsScrapper
            .getSentiments(ticker) / 100.0;

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

    double sentiment = (((double) (pos - neg) / (double) total) + 1.0);
    if (sentiment > 1.0) {
      sentiment = 1.0;
    }

    double difference = (sentiment - bullish) / 6.0;
    double finalSentiment = bullish + difference;

    return finalSentiment;
  }

  public static void main(String[] args) {
    SentimentWrapper sw = new SentimentWrapper();
    System.out.println(sw.findSentimentOf("ETSY"));
  }
}
