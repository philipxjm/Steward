package edu.steward.handlers.json;

import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Creates session for user, setting name and id.
 * 
 * Created by kjin on 4/23/17.
 */
public class LoginHandler implements Route {

  @Override
  public Object handle(Request request, Response response) {
    QueryParamsMap qm = request.queryMap();
    request.session().attribute("user", qm.value("name"));
    request.session().attribute("id", qm.value("id"));
    return "";
  }
}
