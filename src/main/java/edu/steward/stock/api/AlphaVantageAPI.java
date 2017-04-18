package edu.steward.stock.api;

import edu.steward.main.JSONRetriever;
import edu.steward.stock.Fundamental;
import edu.steward.stock.Price;
import edu.steward.stock.api.StockAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by mrobins on 4/17/17.
 */
public class AlphaVantageAPI implements StockAPI {

  @Override
  public List<Price> getStockPrices(String ticker, int startTime, int endTime) {
    return null;
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker, int startTime, int endTime) {
    return null;
  }

  @Override
  public List<Fundamental> getGraphData(String ticker, int startTime, int endTime) {
    return null;
  }

  public String getFromAlphaVantage(Enum... args) {
    for (Enum e : args) {
    }
    String url = constructURL(args);
    System.out.println("url is: " + url);
    return JSONRetriever.getJSON(url, 5000);
  }

  private String constructURL(Enum... args) {
    String url = "";
    try {
      url = "http://www.alphavantage.co/query?function=";
              for (Enum e : args) {
                switch (getType(e)) {
                  case ("FUNCTION"):
                    url +=
                            URLEncoder.encode(
                                    e.toString(),
                                    "UTF-8");
                    break;
                  case ("SYMBOL"):
                    url += "&symbol=";
                    url += URLEncoder.encode(e.toString(),"UTF-8");
                    break;
                  case ("INTERVAL"):
                    url += "&interval=";
                    url += URLEncoder.encode(e.toString(),"UTF-8");
                    break;
                  case ("OUTPUT_SIZE"):
                    url += "&outputsize=";
                    url += URLEncoder.encode(e.toString(),"UTF-8");
                    break;
                  case ("APIKEY"):
                    url += "&apikey=";
                    url += URLEncoder.encode(e.toString(),"UTF-8");
                }
              }
    } catch (UnsupportedEncodingException e) {
      System.out.println("Error with API Call URL");
    }
    return url;
  }

  private static String getType(Enum e) {
    String[] type = e.getClass().getName().split("\\$");
    return type[1];
  }

}
