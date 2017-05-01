package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.stock.Stock;
import edu.steward.stock.api.YahooFinanceAPI;
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
    System.out.println("action handler called!");
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("port");
    String ticker = qm.value("ticker");
    String action = qm.value("action");
    Integer time = (int) (Long.parseLong(qm.value("time")) / 1000);
    Integer shares = (int) Long.parseLong(qm.value("shares"));
    System.out.println("time: " + time);
    System.out.println("shares: " + shares);
    Stock stock = new Stock(ticker);
    Double price = stock.getCurrPrice().getValue();
    Portfolio port = user.getPortfolio(portfolioName);
    System.out.println("pfName: " + port);
    boolean success;
    if (port == null) {
      success = false;
    } else if (action.equals("buy")) {
      System.out.println("buy called");
      success = port.buyStock(ticker, shares, time, price);
    } else {
      System.out.println("Sell called");
      success = port.sellStock(ticker, shares, time, price);
    }
    System.out.println("made it here which is nice");
    return gson.toJson(success);
  }

}
