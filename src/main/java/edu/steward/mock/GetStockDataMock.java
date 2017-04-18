package edu.steward.mock;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.steward.stock.StockAPI.MockStockAPI;
import edu.steward.stock.Price;
import edu.steward.stock.StockAPI.StockAPI;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetStockDataMock implements Route {
  private static StockAPI stockAPI = new MockStockAPI();
	private static final Gson GSON = new Gson();
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String ticker = qm.value("ticker");

      int start = Integer.parseInt(qm.value("start"));
      int end = Integer.parseInt(qm.value("end"));

//      List<List<Double>> ret = new ArrayList<>();
//      double p = 50;
//      double inter = (end - start) / 100.0;
//
//      for (double i = start; i <= end; i += inter) {
//        double newP = p + Math.random() * 10 - 5;
//        ret.add(ImmutableList.of(i, newP));
//        p = newP;
//      }

      List<Price> priceList = stockAPI.getStockPrices(ticker, start, end);

      List<List<Double>> ret = new ArrayList<>();
      for (Price price : priceList) {
        ret.add(ImmutableList.of(
                (double) price.getTime(),
                price.getValue()));
      }

      return GSON.toJson(ret);
    }
}
