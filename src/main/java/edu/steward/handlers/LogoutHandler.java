package edu.steward.handlers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

/**
 * Created by kjin on 4/23/17.
 */
public class LogoutHandler implements Route {

  @Override
  public Object handle(Request req, Response res) {
    req.session().removeAttribute("user");
    return null;
  }
}
