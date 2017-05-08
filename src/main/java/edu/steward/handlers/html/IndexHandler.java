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
      String pic = req.session().attribute("pic");
      User user = new User(id);
      List<Portfolio> portNames = user.getPortfolios();
      List<Portfolio> pools = user.getPoolPorts();
      List<Holding> stocks = new ArrayList<>();

      List<Portfolio> active = new ArrayList<>();
      List<Portfolio> inactive = new ArrayList<>();
      for (Portfolio p : pools) {
        if (p.isDead()) {
          System.out.println(p.getName() + " yup it's dead");
          inactive.add(p);
        } else {
          System.out.println(p.getName() + " not dead, alive!");
          active.add(p);
        }
      }

//      TODO: this is what i had before
//      Map<Object, Object> variables = ImmutableMap.builder()
//          .put("title", "Dashboard").put("user", name).put("pic", pic)
//          .put("pools", pools).put("portfolios", portNames)
//          .put("stocks", stocks).put("id", id).build();

      Map<Object, Object> variables = ImmutableMap.builder()
              .put("title", "Dashboard").put("user", name).put("pic", pic)
              .put("pools", pools).put("portfolios", portNames)
              .put("inactive", inactive)
              .put("stocks", stocks).put("id", id).build();
      return new ModelAndView(variables, "dashboard.ftl");
    } else {
      Map<String, String> variables = ImmutableMap.of("title", "Steward");
      return new ModelAndView(variables, "index.ftl");
    }
  }
}
