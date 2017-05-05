package edu.steward.Sentiment;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Philip on 5/2/17.
 */
public class StockTwitSentimentsScrapper {
  public static double getSentiments(String ticker) {
    String url = "https://stocktwits.com/symbol/" + ticker;
    try {
      Connection con = Jsoup.connect(url)
          .userAgent(
              "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
      con.timeout(180000).ignoreHttpErrors(true).followRedirects(true);
      Connection.Response resp = con.execute();
      if (resp.statusCode() == 200) {
        Document doc = con.get();
        double bullish = Double.parseDouble(
                doc.select("span.bullish").first().text().replace("%", ""));
        return bullish;
      } else {
        return -100.0;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return 0.0;
    }
  }
}
