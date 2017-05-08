package edu.steward.Sentiment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Philip on 5/1/17.
 */
public class StockTwitSearcher {
  public StockTwitSearcher() {
    // not called.
  }

  public List<String> search(String keyword) {
    try {
      return cleanTwits(stockTwits(keyword));
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<String> cleanTwits(List<String> twits) {
    List<String> cleanTwits = new ArrayList<>(twits);
    for (int i = 0; i < cleanTwits.size(); i++) {
      cleanTwits.set(i, removeCashtag(removeUrl(cleanTwits.get(i))));
    }
    return cleanTwits;
//    return twits;
  }

  private static String removeCashtag(String commentstr) {
    String[] words = commentstr.split("\\s+");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      if (word.charAt(0) != '$') {
        sb.append(word);
        sb.append(" ");
      }
    }
    return sb.toString();
  }

  private static String removeUrl(String commentstr) {
    return commentstr.replaceAll("https?://\\S+\\s?", "");
  }

  private List<String> stockTwits(String Ticker) throws ParseException,
          IOException {
    InputStream is = new URL("https://api.stocktwits" +
            ".com/api/2/streams/symbol/"+Ticker+".json?-filter=links")
            .openStream();
    BufferedReader rd
            = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
    String jsonText = readAll(rd);

    ArrayList<String> lst_body = new ArrayList<String>();
    ArrayList<String> lst_time = new ArrayList<String>();
    JsonElement jelement = new JsonParser().parse(jsonText);
    JsonObject  jobject = jelement.getAsJsonObject();
    JsonArray jarray = jobject.getAsJsonArray("messages");

    String result;
    String time;

    for(int i = 0; i < 30; i++) {
      result= jarray.get(i).getAsJsonObject().get("body").toString();

      time = jarray.get(i).getAsJsonObject().get("created_at").toString();

      lst_body.add(result.substring(1,result.length()-1));
      lst_time.add(time.substring(time.indexOf("T")+1,time.indexOf("Z")));
    }

    return lst_body;
  }

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
}
