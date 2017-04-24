package edu.steward.handlers;

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
    request.session().attribute("user", qm.value("id"));

    return "";
  }
}
