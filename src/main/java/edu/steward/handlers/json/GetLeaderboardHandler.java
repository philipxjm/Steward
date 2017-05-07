package edu.steward.handlers.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.steward.pools.Pool;
import edu.steward.sql.DatabaseApi;
import edu.steward.sql.LeaderBoard;
import edu.steward.user.LBscore;
import edu.steward.user.Portfolio;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by kjin on 5/5/17.
 */
public class GetLeaderboardHandler implements Route {
  private final Gson gson = new Gson();

  @Override
  public String handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String poolId = qm.value("poolId");
    Pool pool = DatabaseApi.getPool(poolId)
    List<Portfolio> rankedPortfolios = pool.getPortfolios();
    List<Score> scores = new ArrayList<>();
    List<LBscore> lBscores = LeaderBoard.getLeaderBoard(poolId);
    Long endTime = pool.getEnd();
    Boolean dead = false;
    if (lBscores.size() == 0) {
      for (LBscore lb : lBscores) {
        scores.add(new Score(lb.getPort(), lb.getScore()));
      }
    } else {
      dead = true;
      for (Portfolio p : rankedPortfolios) {
        System.out.println(p);
        System.out.println(p.getNetWorth());
        scores.add(new Score(p, p.getNetWorth()));
      }
    }

    Collections.sort(scores);
    List<Object> l = new ArrayList<>();
    for (Score s : scores) {
      System.out.println(poolId);
      Map<String, String> info = s.getPortfolio().getUser();
      System.out.println(info);
      l.add(ImmutableMap.of("user", info.get("user"), "balance",
          s.getNetWorth(), "pic", info.get("pic"), "userId", info.get("id"),
              "dead", dead));
    }
    return gson.toJson(l);
  }

  private class Score implements Comparable<Score> {
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
      return o.netWorth.compareTo(netWorth);
    }
  }
}
