package edu.steward.user;

import com.google.gson.Gson;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeletePortfolioHandler implements Route {
  private final Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) throws Exception {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("name");
    return GSON.toJson(user.deletePortfolio(portfolioName));
  }

}
