package edu.steward.handlers.json;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import edu.steward.sql.DatabaseApi;
import edu.steward.sql.GainsOverTime;
import edu.steward.user.Holding;
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
public class GetTransactionHistoryHandler implements Route{

  private final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    boolean isPool = Boolean.parseBoolean(qm.value("isPool"));
    String portfolioName = isPool ? "pool/" + qm.value("port") : qm.value
        ("port");
    System.out.println("getting transaction history for " + portfolioName);
    TreeMultimap<String, Holding> history = GainsOverTime.getTransactionHistory
        (userId + "/" +
        portfolioName);
    System.out.println(history);
    return gson.toJson(history.asMap());
  }
}
