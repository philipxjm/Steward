package edu.steward.handlers.json;

import com.google.gson.Gson;

import edu.steward.user.User;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class RenamePortfolioHandler implements Route {

  Gson GSON = new Gson();

  @Override
  public Object handle(Request req, Response res) {
    String userId = req.session().attribute("id");
    User user = new User(userId);
    QueryParamsMap qm = req.queryMap();
    String oldName = qm.value("old");
    String newName = qm.value("new");
    return GSON.toJson(user.renamePortfolio(oldName, newName));
  }
}
