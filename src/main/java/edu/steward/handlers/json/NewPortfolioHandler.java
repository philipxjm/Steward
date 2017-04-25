package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * API end point for adding a portfolio for a given user.
 * 
 * Created by kjin on 4/23/17.
 */
public class NewPortfolioHandler implements Route {

  Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("name");
    return GSON.toJson(user.addPortfolio(portfolioName));
  }
}
