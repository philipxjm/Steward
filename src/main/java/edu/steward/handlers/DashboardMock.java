package edu.steward.handlers;


import java.util.Map;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class DashboardMock implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
	String test = req.session().attribute("user");
    Map<String, String> variables = ImmutableMap.of("title", "Dashboard", "user", "John Smith", "id", test);
    return new ModelAndView(variables, "dashboard.ftl");
  }
}
