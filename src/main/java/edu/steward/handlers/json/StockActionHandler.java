package edu.steward.handlers.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.steward.stock.Stock;
import edu.steward.stock.Fundamentals.Price;
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
    String ticker = qm.value("ticker");
    String action = qm.value("action");
    boolean pool = Boolean.parseBoolean(qm.value("isPool"));
    String portfolioName = pool ? "pool/" + qm.value("port") : qm.value
        ("port");
    Integer transTime = (int) (Long.parseLong(qm.value("time")) / 1000);
    Integer time = transTime;
    Integer shares = (int) Long.parseLong(qm.value("shares"));
    Stock stock = new Stock(ticker);

    // TODO: This should not be currPrice

    Boolean current = Boolean.parseBoolean(qm.value("current"));

    Price priceObj;
    if (current) {
      priceObj = stock.getCurrPrice();
    } else {
      time = transTime - (transTime % 86400) + 72000;
      priceObj = stock.getPrice(time);
      if (priceObj == null) {
        return gson.toJson(ImmutableMap.of("success", false, "error",
            "Stock did not exist at specified time."));
      }
    }
    // No such ticker
    if (priceObj == null) {
      return gson
          .toJson(ImmutableMap.of("success", false, "error", "No such ticker"));
    }
    double price = priceObj.getValue();

    Portfolio port = user.getPortfolio(portfolioName);
    boolean success;
    // No such portfolio
    if (port == null) {
      return gson.toJson(
          ImmutableMap.of("success", false, "error", "No such portfolio"));
    } else if (action.equals("buy")) {
      if (success = port.buyStock(ticker, shares, time, price)) {
        return gson.toJson(ImmutableMap.of("success", success));
      } else {
        return gson.toJson(ImmutableMap.of("success", success, "error",
            "Not enough money to buy"));
      }
    } else {
      if (success = port.sellStock(ticker, shares, time, price)) {
        return gson.toJson(ImmutableMap.of("success", success));
      } else {
        return gson.toJson(ImmutableMap.of("success", success, "error",
            "Not enough stocks to sell"));
      }
    }
  }
}
