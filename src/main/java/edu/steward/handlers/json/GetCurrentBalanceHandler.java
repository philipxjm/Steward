package edu.steward.handlers.json;

import com.google.gson.Gson;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetCurrentBalanceHandler implements Route {
  private static final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    // qm
    return "";
  }

}
