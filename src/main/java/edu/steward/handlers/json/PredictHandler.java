package edu.steward.handlers.json;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.steward.NeuralNet.MLPNetwork;
import edu.steward.stock.Stock;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.StockAPI.TIMESERIES;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class PredictHandler implements Route {
  final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    String ticker = qm.value("ticker");
    MLPNetwork mlp = new MLPNetwork(15);
    mlp.readModel("data/technology/AAPL");
    // TODO: Change to adjusted close?
    Stock stock = new Stock("AAPL");
    List<Price> prices = stock.getStockPrices(TIMESERIES.ONE_MONTH);
    Price p = mlp.run(prices.subList(prices.size() - 15, prices.size()), null);
    return gson.toJson(ImmutableList.of(p.getValue(), p.getTime()));
  }
}
