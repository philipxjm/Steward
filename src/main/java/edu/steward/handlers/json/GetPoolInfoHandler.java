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
    QueryParamsMap qm = req.queryMap();
    String name = qm.value("name");
    String poolId = qm.value("poolId");
    String portId = req.session().attribute("id") + "/pool/" + name;
    Double curr = DatabaseApi.getBalanceFromPortfolio(portId);
    Integer init = DatabaseApi.getPool(poolId).getBal();
    return gson.toJson(ImmutableMap.of("curr", curr,
            "init", init));
  }

}
