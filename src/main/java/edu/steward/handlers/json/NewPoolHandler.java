package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.pools.Pool;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by kjin on 5/2/17.
 */
public class NewPoolHandler implements Route {

  Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String poolId = System.currentTimeMillis() + "/" + qm.value("name");
    String balance = qm.value("balance");
    String start = String.valueOf(System.currentTimeMillis());
    Pool pool = new Pool(poolId, balance, start);
    pool.setEnd(qm.value("end"));
    user.addPortfolio("Pool" + "/" + poolId, poolId);
    System.out.println(qm);
    return GSON.toJson(true);
  }
}
