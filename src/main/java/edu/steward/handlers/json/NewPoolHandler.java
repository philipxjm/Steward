package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.pools.Pool;
import edu.steward.sql.DatabaseApi;
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
    String poolName = qm.value("name");
    System.out.println("pool namez: " + poolName);
    Integer balance = Integer.parseInt(qm.value("balance"));
    long start = (System.currentTimeMillis() / 1000L);
    long end = Long.parseLong(qm.value("end")) / 1000L;
    Pool pool = new Pool(poolName, balance, start, end);
    if (user.addPool(pool.getId()) == null) {
      return "";
    }
    if (Boolean.parseBoolean(qm.value("ai"))) {
      DatabaseApi.createUser("ai", "AI", "", "img/ai.png");
      User ai = new User("ai");
      ai.addPool(pool.getId());
    }
    return GSON.toJson(pool);
  }
}
