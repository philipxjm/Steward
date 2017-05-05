package edu.steward.handlers.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.steward.sql.DatabaseApi;
import edu.steward.user.Portfolio;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kjin on 5/5/17.
 */
public class GetLeaderboardHandler implements Route{
  private final Gson gson = new Gson();
  @Override
  public String handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String poolId = qm.value("poolId");
    List<Portfolio> rankedPortfolios = DatabaseApi.getPortsFromPool(poolId);
    rankedPortfolios.sort(
        (a,b) ->
        Double.compare(a
        .getNetValue(), b.getNetValue()));
    List<Object> l = new ArrayList<>();
    for (Portfolio p : rankedPortfolios) {
      Map<String, String> info = p.getUser();
      l.add(ImmutableMap.of("user", info.get("id"), "balance", p.getNetValue
          (), "pic", info.get("pic")));
    }
    return gson.toJson(l);
  }
}
