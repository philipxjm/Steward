package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.sql.DatabaseApi;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetPoolInfoHandler implements Route {
  private static final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    String name = qm.value("name");
    System.out.println("name: |" + name + "| this");
    String id = req.session().attribute("id") + "/pool/" + name;
    System.out.println("get curr balance called, id: " + id);
    return gson.toJson(DatabaseApi.getBalanceFromPortfolio(id));
  }

}
