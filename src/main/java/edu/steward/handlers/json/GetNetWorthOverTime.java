package edu.steward.handlers.json;

import java.util.List;

import com.google.gson.Gson;

import edu.steward.sql.DatabaseApi;
import edu.steward.user.Portfolio;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

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
    System.out.println("pool Id " + poolId);
    System.out.println("I GOT HERE");
    System.out.println(portfolios.get(0));
    System.out.println("I DIDNT HERE");
    System.out.println(portfolios.get(0).getPortfolioId());
    System.out.println("5");

    // TODO: this should add it for every user, front end not currently set up
    // to handle it

    // List<Object> l = new ArrayList<>();
    // for (Portfolio p : portfolios) {
    // Map<String, String> info = p.getUser();
    // l.add(ImmutableMap.of("user", info.get("id"), "balance",
    // p.getHistoricalWorth(), "pic", info.get("pic")));
    // }
    return gson.toJson(portfolios.get(0).getHistoricalWorth());
  }
}
