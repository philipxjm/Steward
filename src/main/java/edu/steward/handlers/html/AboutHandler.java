package edu.steward.handlers.html;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Handler returns about page for Steward.
 * 
 * @author wpovell
 */
public class AboutHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    String user = req.session().attribute("user");
    String pic = req.session().attribute("pic");
    String id = req.session().attribute("id");
    Map<String, String> variables;
    if (user != null) {
      variables = ImmutableMap.of("title", "About", "user", user, "pic", pic,
          "id", id);
    } else {
      variables = ImmutableMap.of("title", "About");
    }
    return new ModelAndView(variables, "about.ftl");
  }
}
