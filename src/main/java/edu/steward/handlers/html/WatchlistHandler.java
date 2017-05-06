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
    String pic = req.session().attribute("pic");
    Map<String, Object> variables;
    Map<String, Double> trending = Watchlist.trendingSentiments();
    List<List<Object>> ret = new ArrayList<>();
    for (String key : trending.keySet()) {
      ret.add(ImmutableList.of(key, trending.get(key)));
    }
    ret.sort((List<Object> a, List<Object> b) -> Double
        .compare((double) b.get(1), (double) a.get(1)));
    if (user != null) {
      variables = ImmutableMap.of("title", "Watchlist", "trending", ret, "user",
          user, "pic", pic);
    } else {
      variables = ImmutableMap.of("title", "Watchlist", "trending", ret);
    }
    return new ModelAndView(variables, "watchlist.ftl");
  }
}
