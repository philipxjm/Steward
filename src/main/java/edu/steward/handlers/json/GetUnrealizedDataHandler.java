package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetUnrealizedDataHandler implements Route {
  private final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("name");
    return gson.toJson(user.getPortfolio(portfolioName).getUnrealized());
  }
}
