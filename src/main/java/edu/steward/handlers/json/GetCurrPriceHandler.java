package edu.steward.handlers.json;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import edu.steward.stock.Stock;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by mrobins on 5/5/17.
 */
public class GetCurrPriceHandler implements Route {
  private static final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    String ticker = qm.value("ticker");
    System.out.println(ticker);
    Stock stock = new Stock(ticker);
    try {
      JsonElement jsonElement = gson.toJsonTree(stock.getCurrPrice());
      jsonElement.getAsJsonObject().addProperty("change", stock
              .getDailyChange().getValue());
      return gson.toJson(jsonElement);
//      return gson.toJson(stock.getCurrPrice());
    } catch (StringIndexOutOfBoundsException e) {
      return "";
    }
  }
}
