package edu.steward.handlers;

import java.util.List;

import com.google.common.collect.ImmutableMap;

import edu.steward.stock.Stock;
import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Serves up stock page for given ticker, with related stock essentials.
 * 
 * @author wpovell
 */
public class StockHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String ticker = req.params(":ticker").toUpperCase();

    Stock stock = new Stock(ticker);

    ImmutableMap.Builder<Object, Object> variables = new ImmutableMap.Builder<>();
    List<Fundamental> fundamentals = stock.getStockFundamentals();

    variables.put("ticker", ticker);
    String user = req.session().attribute("user");
    if (user != null) {
      variables.put("user", user);
    }
    if (fundamentals == null) {
      variables.put("title", "Bad Stock");
      return new ModelAndView(variables.build(), "badStock.ftl");
    }

    Price currPrice = stock.getCurrPrice();
    DailyChange dailyChange = stock.getDailyChange();
    String color = "up";
    if (dailyChange.getValue() < 0) {
      color = "down";
    }
    variables.put("color", color).put("fundamentals", fundamentals)
        .put("price", currPrice).put("change", dailyChange)
        .put("title", "Stock: " + ticker).put("css", "/css/graph.css");
    return new ModelAndView(variables.build(), "stock.ftl");
  }
}
