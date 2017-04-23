package edu.steward.handlers;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by kjin on 4/23/17.
 */
public class addPortfolioHandler implements Route {

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("user");
    QueryParamsMap qm = req.queryMap();
    String portfolioName = qm.value("name");
    String portfolioId = userId + "/" + qm.value("index");
    return "";
  }
}
