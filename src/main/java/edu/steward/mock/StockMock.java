package edu.steward.mock;


import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class StockMock implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String ticker = req.params(":ticker");
      
      Map<String, String> variables = ImmutableMap.of("ticker", ticker, "user", "John Smith");
      return new ModelAndView(variables, "stock.ftl");
    }
}
