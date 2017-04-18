package edu.steward.stock.api;

import edu.steward.main.JSONRetriever;
import edu.steward.stock.Fundamental;
import edu.steward.stock.Price;
import edu.steward.stock.api.StockAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by mrobins on 4/17/17.
 */
public class AlphaVantageAPI implements StockAPI {
	private static final Gson GSON = new Gson();
	
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
  
  public Map<String, Map<String,Double>> parseAlphaVantage(String json) {
	  JsonObject received = GSON.fromJson(json, JsonObject.class);
	  JsonObject meta = received.get("Meta Data").getAsJsonObject();
	  
	  // Meta
	  String info = meta.get("1. Information").getAsString();
	  String sym = meta.get("2. Symbol").getAsString();
	  String last = meta.get("3. Last Refreshed").getAsString();
	  String inter = meta.get("4. Interval").getAsString();
	  String output = meta.get("5. Output Size").getAsString();
	  String timezone = meta.get("6. Time Zone").getAsString();
	  
	  // Timeseries
	  JsonObject timeseries = received.get("Time Series (15min)").getAsJsonObject();
	  Map<String, Map<String,Double>> ret = new HashMap<>();
	  for(Map.Entry<String, JsonElement> e : timeseries.entrySet()) {
		  Map<String, Double> toAdd = new HashMap<>();
		  JsonObject obj = (JsonObject) e.getValue();
		  toAdd.put("open", obj.get("1. open").getAsDouble());
		  toAdd.put("high", obj.get("2. high").getAsDouble());
		  toAdd.put("low", obj.get("3. low").getAsDouble());
		  toAdd.put("close", obj.get("4. close").getAsDouble());
		  toAdd.put("volume", obj.get("5. volume").getAsDouble());
		  ret.put(e.getKey(), toAdd);
	  }
	  return ret;
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
