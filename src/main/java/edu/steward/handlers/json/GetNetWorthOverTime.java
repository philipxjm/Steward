package edu.steward.handlers.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.steward.sql.DatabaseApi;
import edu.steward.stock.Fundamentals.Gains;
import edu.steward.user.Portfolio;
import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by mrobins on 5/5/17.
 */
public class GetNetWorthOverTime implements Route {
  private final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String poolId = qm.value("poolId");
    List<Portfolio> portfolios = DatabaseApi.getPortsFromPool(poolId);
    System.out.println("portfolio size: " + portfolios.size());
    System.out.println(portfolios.get(0).getBalance());
    System.out.println(portfolios.get(0).getPortfolioId());
    System.out.println("5");

//    TODO: this should add it for every user, front end not currently set up to handle it

//    List<Object> l = new ArrayList<>();
//    for (Portfolio p : portfolios) {
//      Map<String, String> info = p.getUser();
//      l.add(ImmutableMap.of("user", info.get("id"), "balance", p.getHistoricalWorth(), "pic", info.get("pic")));
//    }
    return gson.toJson(portfolios.get(0).getHistoricalWorth());
  }
}