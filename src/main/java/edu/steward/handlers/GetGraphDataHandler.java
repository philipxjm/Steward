package edu.steward.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.steward.stock.Stock;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.StockAPI.TIMESERIES;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * API end point for getting stock price data to graph for the given timeseries.
 * 
 * @author wpovell
 *
 */
public class GetGraphDataHandler implements Route {

  private static final Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String ticker = qm.value("ticker");
    TIMESERIES timeseries = TIMESERIES.valueOf(qm.value("timeseries"));
    Stock stock = new Stock(ticker);
    List<Price> prices = stock.getStockPrices(timeseries);
    System.out.println("size iz: " + prices.size());
    Collections.sort(prices, new Comparator<Price>() {
      @Override
      public int compare(Price o1, Price o2) {
        return o1.getTime().compareTo(o2.getTime());
      }
    });

    System.out.println("timeseries: " + timeseries);

    List<List<Object>> ret = new ArrayList<>();
    for (Price p : prices) {
      System.out.println(p.getTime() + ", " + p.getValue());
      ret.add(ImmutableList.of((Object) p.getTime(), (Object) p.getValue()));
    }

    return GSON.toJson(ret);
  }
}
