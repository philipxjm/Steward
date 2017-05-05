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
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kjin on 5/5/17.
 */
public class GetLeaderboardHandler implements Route {
  private final Gson gson = new Gson();
  @Override
  public String handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String poolId = qm.value("poolId");
    List<Portfolio> rankedPortfolios = DatabaseApi.getPortsFromPool(poolId);
    List<Score> scores = new ArrayList<>();
    for (Portfolio p : rankedPortfolios) {
      System.out.println("once");
      scores.add(new Score(p, p.getNetWorth()));
    }
    Collections.sort(scores);
    List<Object> l = new ArrayList<>();
    for (Score s : scores) {
      Map<String, String> info = s.getPortfolio().getUser();
      l.add(ImmutableMap.of("user", info.get("id"), "balance", s.getNetWorth(), "pic", info.get("pic")));
    }
    return gson.toJson(l);
  }

  private class Score implements Comparable<Score>{
    private Portfolio portfolio;
    private Double netWorth;

    public Score(Portfolio portfolio, Double netWorth) {
      this.portfolio = portfolio;
      this.netWorth = netWorth;
    }

    public Portfolio getPortfolio() {
      return portfolio;
    }

    public Double getNetWorth() {
      return netWorth;
    }

    @Override
    public int compareTo(Score o) {
      return netWorth.compareTo(o.netWorth);
    }
  }
}
