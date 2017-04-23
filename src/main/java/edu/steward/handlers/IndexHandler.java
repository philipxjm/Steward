package edu.steward.handlers;


import java.util.Map;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class IndexHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String test = req.session().attribute("user");
    boolean loggedIn = test != null;
    if (loggedIn) {
      Map<String, String> variables = ImmutableMap.of("title", "Dashboard", "user", "John Smith", "id", test);
      return new ModelAndView(variables, "dashboard.ftl");
    } else {
      Map<String, String> variables = ImmutableMap.of("title", "Steward");
      return new ModelAndView(variables, "index.ftl");
    }
  }
}
