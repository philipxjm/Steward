package edu.steward.handlers;


import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.Stock;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class StockMock implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String ticker = req.params(":ticker").toUpperCase();

    Stock stock = new Stock(ticker);
    Price currPrice = stock.getCurrPrice();
    DailyChange dailyChange = stock.getDailyChange();

    List<Fundamental> fundamentals = stock.getStockFundamentals();
    String color = "up";
    if (dailyChange.getValue() < 0) {
    	color = "down";
    }
    ImmutableMap<Object, Object> variables = new ImmutableMap.Builder<>()
            .put("ticker", ticker)
            .put("color",color)
            .put("fundamentals", fundamentals)
            .put("price", currPrice)
            .put("change", dailyChange)
            .put("title", "Stock: " + ticker)
            .put("css", "/css/graph.css")
            .put("user", "John Smith")
            .build();
    return new ModelAndView(variables, "stock.ftl");
  }
}
