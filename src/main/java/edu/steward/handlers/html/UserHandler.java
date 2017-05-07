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
    String id = req.session().attribute("id");
    String userId = req.params(":id");
    System.out.println("AAA");
    System.out.println(userId);
    Map<String, String> userInfo = DatabaseApi.getUserInfo(userId);
    System.out.println(userInfo);
    Map<String, String> ret = new HashMap<>();
    ret.putAll(userInfo);
    ret.put("userPic", ret.get("pic"));
    ret.put("pic", pic);
    ret.put("title", "User: " + name);
    ret.put("id", id);
    return new ModelAndView(ret, "account.ftl");
  }
}
