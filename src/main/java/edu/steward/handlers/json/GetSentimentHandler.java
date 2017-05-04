package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.Sentiment.SentimentWrapper;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetSentimentHandler implements Route {
  private static final Gson gson = new Gson();
  private SentimentWrapper sentiment;

  public GetSentimentHandler(SentimentWrapper w) {
    sentiment = w;
  }

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    String ticker = qm.value("ticker");
    return gson.toJson(sentiment.findSentimentOf(ticker));
  }

}
