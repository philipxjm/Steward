package edu.steward.handlers;

import com.google.common.collect.ImmutableMap;

import edu.steward.stock.Stock;
import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Price;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Created by mrobins on 4/22/17.
 */
public class CurrentPriceHandler {
  public class StockMock implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String ticker = req.params(":ticker").toUpperCase();

      Stock stock = new Stock(ticker);
      Price currPrice = stock.getCurrPrice();
      DailyChange dailyChange = stock.getDailyChange();

      ImmutableMap<Object, Object> variables = new ImmutableMap.Builder<>()
          .put("ticker", ticker).put("Price", currPrice)
          .put("Daily Change", dailyChange).put("title", "Stock: " + ticker)
          .put("css", "/css/graph.css").put("user", "John Smith").build();
      // Map<String, Object> variables = ImmutableMap.of("ticker", ticker,
      // "Price", currPrice,
      // "Daily Change", dailyChange, "title", "Stock: " + ticker, "css",
      // "/css/graph.css", "user", "John Smith");
      return new ModelAndView(variables, "stock.ftl");
    }
  }
}
