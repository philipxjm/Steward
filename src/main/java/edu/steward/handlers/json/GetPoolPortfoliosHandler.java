package edu.steward.handlers.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kjin on 5/5/17.
 */
public class GetPoolPortfoliosHandler implements Route {

  private static final Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String name = qm.value("name");
    User user = new User(req.session().attribute("id"));
    Map<String, Integer> stocks = user.getPortfolio(name).getHoldings();
    List<Map<String, Object>> ret = new ArrayList<>();
    for (String ticker : stocks.keySet()) {
      System.out.println("ticker: " + ticker + " shares: " + stocks.get(ticker));
      ret.add(ImmutableMap.of("ticker", ticker, "shares",
          stocks.get(ticker)));
    }
    return GSON.toJson(ret);
  }
}
