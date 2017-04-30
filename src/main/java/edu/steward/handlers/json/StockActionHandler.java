package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.user.Portfolio;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class StockActionHandler implements Route {
  private final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("port");
    String ticker = qm.value("ticker");
    String action = qm.value("action");
    int time = Integer.parseInt(qm.value("time"));
    int shares = Integer.parseInt(qm.value("shares"));
    Portfolio port = user.getPortfolio(portfolioName);
    System.out.println(portfolioName);
    boolean success;
    if (port == null) {
      success = false;
    } else if (action.equals("buy")) {
      success = port.buyStock(ticker, shares, time, 0);
    } else {
      success = port.sellStock(ticker, shares, time, 0);
    }
    return gson.toJson(success);
  }

}
