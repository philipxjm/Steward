package edu.steward.handlers;


import java.util.Map;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class AboutHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, String> variables = ImmutableMap.of("title", "About");
    return new ModelAndView(variables, "about.ftl");
  }
}
