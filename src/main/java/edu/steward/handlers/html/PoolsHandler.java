package edu.steward.handlers.html;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class PoolsHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    String name = req.session().attribute("user");
    if (name != null) {
      return new ModelAndView(ImmutableMap.of("user", name, "title", "Pools"),
          "pools.ftl");
    }
    return new ModelAndView(ImmutableMap.of("title", "Pools"), "pools.ftl");
  }

}
