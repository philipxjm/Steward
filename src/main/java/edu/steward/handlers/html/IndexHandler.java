package edu.steward.handlers.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.steward.user.Holding;
import edu.steward.user.Portfolio;
import edu.steward.user.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Serves up landing page if logged in, dashboard otherwise.
 * 
 * @author wpovell
 *
 */
public class IndexHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String name = req.session().attribute("user");

    boolean loggedIn = name != null;
    if (loggedIn) {
      String id = req.session().attribute("id");
      User user = new User(id);
      List<Portfolio> portNames = user.getPortfolios();
      List<Portfolio> pools = user.getPoolPorts();
      List<Holding> stocks = new ArrayList<>();
      if (!portNames.isEmpty()) {
        Map<String, Integer> m = portNames.get(0).getHoldings();
        for (String ticker : m.keySet()) {
          Holding h = new Holding(ticker, m.get(ticker));
          if (h.getShares() > 0) {
            stocks.add(h);
          }
        }
      }

      Map<String, Object> variables = ImmutableMap.of("title", "Dashboard",
          "user", name, "pools", pools, "portfolios", portNames, "stocks",
          stocks);
      return new ModelAndView(variables, "dashboard.ftl");
    } else {
      Map<String, String> variables = ImmutableMap.of("title", "Steward");
      return new ModelAndView(variables, "index.ftl");
    }
  }
}
