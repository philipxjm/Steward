package edu.steward.Sentiment;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Philip on 5/1/17.
 */
public class SentimentWrapper {

  private TwitterSentimentFinder tsf;

  public SentimentWrapper() {
    tsf = new TwitterSentimentFinder();
  }

  public double findSentimentOf(String ticker) {
    try {
      if (SentimentCache.doesContainUpToDate(ticker)) {
        return SentimentCache.getFromCache(ticker);
      } else {
        List<Integer> sentiments
                = tsf.sentiments(ImmutableList.of(ticker)).get(ticker);
        double finalSentiment = sentimentNormalizer(ticker, sentiments);
        SentimentCache.insertToCache(ticker, finalSentiment);
        return finalSentiment;

//      Mock Data Below

//      double finalSentiment = Math.random();
//      SentimentCache.insertToCache(ticker, finalSentiment);
//      return finalSentiment;
      }
    } catch (Exception e) {
      System.out.println("Sentiment generation failure, executing order 66.");
      return Math.random();
    }
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

    if (bullish >= 0) {
      double difference = (sentiment - bullish) / 6.0;
      return bullish + difference;
    } else {
      return sentiment;
    }
  }
}
