package edu.steward.handlers.json;

import java.io.File;
import java.util.ArrayList;
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

public class GetStockPredictionHandler implements Route {
  private static final Gson GSON = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    String ticker = qm.value("ticker");
    String path = "data/technology/" + ticker;
    File f = new File(path);
    if (f.exists() && !f.isDirectory()) {
      int n;
      if (ticker.toUpperCase().equals("AAPL")) {
        n = 15;
      } else {
        n = 5;
      }
      MLPNetwork mlp = new MLPNetwork(n);
      mlp.readModel(path);
      // TODO: Change to adjusted close?
      Stock stock = new Stock(ticker);
      List<Price> predictPrices = stock.getStockPrices(TIMESERIES.ONE_MONTH);
      System.out.println(predictPrices.subList(0, n));
      Price p = mlp.run(
          reverseList(predictPrices.subList(0, n)),
          null);

      return GSON.toJson(ImmutableList.of(p.getTime(),
          Math.round(p.getValue() * 100.0) / 100.0));
    } else {
      return GSON.toJson(null);
    }
  }

  private static List reverseList(List myList) {
    List invertedList = new ArrayList();
    for (int i = myList.size() - 1; i >= 0; i--) {
      invertedList.add(myList.get(i));
    }
    return invertedList;
  }
}
