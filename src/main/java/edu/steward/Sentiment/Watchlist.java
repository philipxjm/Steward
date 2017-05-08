package edu.steward.Sentiment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Philip on 5/2/17.
 */
public class Watchlist {
  public static List<String> trendingSymbols() {
    try {
      InputStream is = new URL("https://api.stocktwits" +
              ".com/api/2/trending/symbols.json")
      .openStream();
      BufferedReader rd
              = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      StringBuilder result = new StringBuilder();
      String line;
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      rd.close();

      JsonElement jelement = new JsonParser().parse(result.toString());
      JsonObject  jobject = jelement.getAsJsonObject();
      JsonArray jarray = jobject.getAsJsonArray("symbols");

      List<String> symbols = new ArrayList<>();
      for(int i = 0; i < jarray.size(); i++) {
        String symbol = jarray.get(i).getAsJsonObject().get("symbol")
                .toString().replaceAll("^\"|\"$", "");
        if (!(symbol.contains(".") || symbol.contains("^"))) {
          symbols.add(symbol);
        }
      }
      if (symbols.size() > 20) {
        symbols = symbols.subList(0, 20);
      }
      return symbols;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Map<String, Double> trendingSentiments() {
    SentimentWrapper sw = new SentimentWrapper();
    List<String> trendingSymbols = trendingSymbols();
    Map<String, Double> trendingSentiments = new HashMap<>();
    assert trendingSymbols != null;
    for (String symbol : trendingSymbols) {
      trendingSentiments.put(symbol, sw.findSentimentOf(symbol));
    }
    return trendingSentiments;
  }
}
