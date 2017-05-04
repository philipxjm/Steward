package edu.steward.Sentiment;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Philip on 5/2/17.
 */
public class StockTwitSentimentsScrapper {
  public static double getSentiments(String ticker) {
    String url = "https://stocktwits.com/symbol/" + ticker;
    try {
      Document doc = Jsoup.connect(url)
          .userAgent(
              "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
          .get();
      double bullish = Double.parseDouble(
          doc.select("span.bullish").first().text().replace("%", ""));
      return bullish;
    } catch (IOException e) {
      e.printStackTrace();
      return 0.0;
    }
  }
}
