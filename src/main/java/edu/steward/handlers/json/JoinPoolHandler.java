package edu.steward.handlers.json;

import com.google.gson.Gson;
import edu.steward.sql.DatabaseApi;
import edu.steward.sql.LeaderBoard;
import edu.steward.user.Portfolio;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by kjin on 5/2/17.
 */
public class JoinPoolHandler implements Route {

  Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String poolId = qm.value("name");

    if (LeaderBoard.getLeaderBoard(poolId).size() > 0) {
      return GSON.toJson(null);
    } else {
      Portfolio p = user.addPool(poolId);
      if (p == null) {
        return GSON.toJson(null);
      } else {
        return GSON.toJson(p);
      }
    }
  }
}
