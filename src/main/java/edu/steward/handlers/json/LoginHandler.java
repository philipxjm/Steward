package edu.steward.handlers.json;

import edu.steward.sql.DatabaseApi;
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
    String name = qm.value("name");
    String id = qm.value("id");
    String email = qm.value("email");
    String pic = qm.value("pic");
    DatabaseApi.createUser(id, name, email, pic);
    request.session().attribute("user", qm.value("name"));
    request.session().attribute("id", qm.value("id"));
    return "";
  }
}
