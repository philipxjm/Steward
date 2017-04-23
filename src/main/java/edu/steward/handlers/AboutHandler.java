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

	String user = req.session().attribute("user");
	Map<String, String> variables;
	if (user != null) {
		variables = ImmutableMap.of("title", "About", "user", user);
	} else {
		variables = ImmutableMap.of("title", "About");
	}
    return new ModelAndView(variables, "about.ftl");
  }
}
