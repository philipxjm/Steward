package edu.steward.handlers;

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

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("user");
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("name");
    String portfolioId = userId + "/" + qm.value("index");
    return "";
  }
}
