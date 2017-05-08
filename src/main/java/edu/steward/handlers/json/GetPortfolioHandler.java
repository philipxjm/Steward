package edu.steward.handlers.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.steward.stock.Stock;
import edu.steward.user.Holding;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetPortfolioHandler implements Route {

  private static final Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    boolean pool = Boolean.parseBoolean(qm.value("isPool"));
    String portfolioName = pool ? "pool/" + qm.value("name") : qm.value
        ("name");
    User user = new User(req.session().attribute("id"));
    Map<String, Integer> stocks = user.getPortfolio(portfolioName).getHoldings();
    List<Map<String, Object>> ret = new ArrayList<>();
    for (String ticker : stocks.keySet()) {
      Stock stock = new Stock(ticker);

      ret.add(ImmutableMap.of("ticker", ticker, "shares", stocks.get(ticker),
              "currPrice", stock.getCurrPrice(), "change", stock.getDailyChange()));
    }
    return GSON.toJson(ret);
  }
}
