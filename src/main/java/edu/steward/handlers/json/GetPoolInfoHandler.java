package edu.steward.handlers.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.steward.pools.Pool;
import edu.steward.sql.DatabaseApi;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetPoolInfoHandler implements Route {
  private static final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) throws Exception {
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    QueryParamsMap qm = req.queryMap();
    String name = qm.value("name");
    System.out.println(name);
    String poolId = qm.value("poolId");
    System.out.println(poolId);
    String portId = req.session().attribute("id") + "/pool/" + name;
    System.out.println(portId);
    Double curr = DatabaseApi.getBalanceFromPortfolio(portId);
    System.out.println("currr: " + curr);
    Integer init = DatabaseApi.getPool(poolId).getBal();
    System.out.println("initt: " + init);
    System.out.println("made it hereaaa");
    return gson.toJson(ImmutableMap.of("curr", curr,
            "init", init));
  }

}
