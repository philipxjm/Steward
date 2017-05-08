package edu.steward.handlers.html;

import java.util.HashMap;
import java.util.Map;

import edu.steward.sql.DatabaseApi;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class UserHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    String userId = req.params(":id");
    Map<String, String> userInfo = DatabaseApi.getUserInfo(userId);

    Map<String, String> ret = new HashMap<>();
    ret.putAll(userInfo);
    ret.put("userName", ret.get("user"));
    ret.put("userPic", ret.get("pic"));
    if (userId.equals("ai")) {
      ret.put("userPic", "/img/ai.png");
    }

    // Current User
    ret.put("user", req.session().attribute("user"));
    ret.put("id", req.session().attribute("id"));
    ret.put("pic", req.session().attribute("pic"));

    ret.put("title", "User: " + ret.get("user"));
    ret.put("id", userId);
    return new ModelAndView(ret, "account.ftl");
  }
}
