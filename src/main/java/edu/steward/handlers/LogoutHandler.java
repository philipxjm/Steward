package edu.steward.handlers;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Logs out user, removing data from session.
 * 
 * Created by kjin on 4/23/17.
 */
public class LogoutHandler implements Route {

  @Override
  public Object handle(Request req, Response res) {
    req.session().removeAttribute("user");
    req.session().removeAttribute("id");
    return "";
  }
}
