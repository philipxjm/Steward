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
    List<G> gains = new ArrayList<>();
    for (Portfolio p : portfolios) {
      gains.add(new G(p, p.getGainsOverTime()));
    }
    List<Object> l = new ArrayList<>();
    for (G g : gains) {
      Map<String, String> info = g.getPortfolio().getUser();
      l.add(ImmutableMap.of("user", info.get("id"), "balance", g.getGains(), "pic", info.get("pic")));
    }
    return gson.toJson(l);
  }
    private class G {
      private Portfolio portfolio;
      private List<Gains> gains;

      public G(Portfolio portfolio, List<Gains> gains) {
        this.portfolio = portfolio;
        this.gains = gains;
      }

      public Portfolio getPortfolio() {
        return portfolio;
      }

      public List<Gains> getGains() {
        return gains;
      }
    }
  }