package edu.steward.handlers.html;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class WatchlistHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    String user = req.session().attribute("user");
    Map<String, String> variables;
    if (user != null) {
      variables = ImmutableMap.of("title", "Watchlist", "user", user);
    } else {
      variables = ImmutableMap.of("title", "Watchlist");
    }
    return new ModelAndView(variables, "watchlist.ftl");
  }

}
