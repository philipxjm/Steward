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

public class PoolsHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    String name = req.session().attribute("user");

    boolean loggedIn = name != null;
    if (loggedIn) {
      String id = req.session().attribute("id");
      User user = new User(id);
      List<Portfolio> ports = user.getPoolPorts();
      List<Holding> stocks = new ArrayList<>();
      if (!ports.isEmpty()) {
        Map<String, Integer> m = ports.get(0).getHoldings();
        for (String ticker : m.keySet()) {
          Holding h = new Holding(ticker, m.get(ticker));
          if (h.getShares() > 0) {
            stocks.add(h);
          }
        }
      }

      Map<String, Object> variables = ImmutableMap.of("title", "Pools", "user",
          name, "pools", ports, "stocks", stocks);
      System.out.println("HERE");
      return new ModelAndView(variables, "pools.ftl");
    } else {
      // TODO: Show not authorized?
      res.status(401);
      res.redirect("/");
      return new ModelAndView(ImmutableMap.of(), "about.ftl");
    }
  }

}
