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
    String name = req.session().attribute("name");
    String pic = req.session().attribute("pic");
    String userId = req.params(":id");
    Map<String, String> userInfo = DatabaseApi.getUserInfo(userId);
    System.out.println(userInfo);
    Map<String, String> ret = new HashMap<>();
    ret.putAll(userInfo);
    ret.put("userPic", ret.get("pic"));
    if (userId.equals("ai")) {
      ret.put("pic", "/img/ai.png");
    } else {
      ret.put("pic", pic);
    }
    ret.put("title", "User: " + name);
    ret.put("id", userId);
    return new ModelAndView(ret, "account.ftl");
  }
}
