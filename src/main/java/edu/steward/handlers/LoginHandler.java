package edu.steward.handlers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

/**
 * Created by kjin on 4/23/17.
 */
public class LoginHandler implements Route {

  @Override
  public Object handle(Request request, Response response) {

    Gson GSON = new Gson();

    QueryParamsMap qm = request.queryMap();
    request.session().attribute("user", qm.value("id"));

    Map<String, String> variables = ImmutableMap.of("title", "Dashboard",
        "id", qm.value("id"));
    return GSON.toJson(variables);
  }
}
