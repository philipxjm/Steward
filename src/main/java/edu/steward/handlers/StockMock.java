package edu.steward.handlers;


import java.util.Map;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class StockMock implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String ticker = req.params(":ticker").toUpperCase();

    Map<String, String> variables = ImmutableMap.of("ticker", ticker,
    		"title", "Stock: " + ticker, "css", "/css/graph.css", "user", "John Smith");
    return new ModelAndView(variables, "stock.ftl");
  }
}
