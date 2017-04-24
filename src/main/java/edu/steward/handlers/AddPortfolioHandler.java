package edu.steward.handlers;

import com.google.gson.Gson;
import edu.steward.user.Portfolio;
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
public class AddPortfolioHandler implements Route {

  Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("user");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("name");
    Portfolio newPortfolio = new Portfolio(portfolioName, user + "/" + portfolioName);
    return GSON.toJson(user.addPortfolio(newPortfolio));
  }
}
