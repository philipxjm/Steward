package edu.steward.Sentiment;

import com.google.common.collect.ImmutableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 5/2/17.
 */
public class StockTwitSentimentsScrapper {
  public static double getSentiments(String ticker) {
    String url = "https://stocktwits.com/symbol/" + ticker;
    try {
      Document doc = Jsoup.connect(url).get();
      double bullish = Double.parseDouble(doc.select("span.bullish")
              .first()
              .text()
              .replace("%", ""));
      return bullish;
    } catch (IOException e) {
      e.printStackTrace();
      return 0.0;
    }
  }
}
