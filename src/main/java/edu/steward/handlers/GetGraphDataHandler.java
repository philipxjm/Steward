package edu.steward.handlers;


import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.AlphaVantageAPI;
import edu.steward.stock.api.StockAPI;
import edu.steward.stock.api.StockAPI.TIMESERIES;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetGraphDataHandler implements Route {

  private static final Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String ticker = qm.value("ticker");
    TIMESERIES timeseries = TIMESERIES.valueOf(qm.value("timeseries"));
    StockAPI api = new AlphaVantageAPI();
    List<Price> prices = api.getStockPrices(ticker, timeseries);

    List<List<Object>> ret = new ArrayList<>();
    for (Price p : prices) {
      ret.add(ImmutableList.of((Object) p.getTime(), (Object) p.getValue()));
    }

    return GSON.toJson(ret);
  }
}
