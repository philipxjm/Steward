package edu.steward.handlers.html;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.steward.sql.DatabaseApi;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class AccountHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    String id = req.session().attribute("id");
    if (id == null) {
      res.status(401);
      res.redirect("/");
      return new ModelAndView(ImmutableList.of(), "index.ftl");
    }
    Map<String, String> userInfo = DatabaseApi.getUserInfo(id);

    Map<Object, Object> ret = ImmutableMap.builder().putAll(userInfo)
        .put("title", "Account").put("userPic", userInfo.get("pic")).build();
    return new ModelAndView(ret, "account.ftl");
  }

}
