package edu.steward.handlers;


import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import edu.steward.stock.Fundamentals.Fundamental;
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
    List<Fundamental> fundamentals = stock.getStockFundamentals();

    Map<String, Object> variables = ImmutableMap.of("ticker", ticker, "fundamentals", fundamentals,
    		"title", "Stock: " + ticker, "css", "/css/graph.css", "user", "John Smith");
    return new ModelAndView(variables, "stock.ftl");
  }
}
