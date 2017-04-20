package edu.steward.analytics;

/**
 * Created by Philip on 4/19/2017.
 */

import com.google.gson.Gson;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class AnalyticsHandler implements Route {
  private static StockPredictor stockPredictor;

  public static void setStockPredictor(StockPredictor sp) {
    stockPredictor = sp;
  }

  private static final Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    if (stockPredictor != null) {

      QueryParamsMap qm = req.queryMap();
      String ticker = qm.value("ticker");

      return GSON.toJson(stockPredictor.getFiveDayPrediction(ticker));
    } else {
      return null;
    }
  }
}
