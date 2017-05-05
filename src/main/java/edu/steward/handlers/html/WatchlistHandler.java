package edu.steward.handlers.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.steward.Sentiment.Watchlist;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class WatchlistHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    String user = req.session().attribute("user");
    Map<String, Object> variables;
    Map<String, Double> trending = Watchlist.trendingSentiments();
    List<List<Object>> good = new ArrayList<>();
    List<List<Object>> bad = new ArrayList<>();
    for (String ticker : trending.keySet()) {
      Double sentiment = trending.get(ticker);
      List<Object> toAdd = ImmutableList.of(ticker, sentiment);
      if (sentiment > 0.5) {
        good.add(toAdd);
      } else {
        bad.add(toAdd);
      }
      good.sort((List<Object> a, List<Object> b) -> {
        return Double.compare((double) a.get(1), (double) b.get(1));
      });

      bad.sort((List<Object> a, List<Object> b) -> {
        return Double.compare(-(double) a.get(1), -(double) b.get(1));
      });
    }
    if (user != null) {
      variables = ImmutableMap.of("title", "Watchlist", "good", good, "bad",
          bad, "user", user);
    } else {
      variables = ImmutableMap.of("title", "Watchlist", "good", good, "bad",
          bad);
    }
    return new ModelAndView(variables, "watchlist.ftl");
  }
}
