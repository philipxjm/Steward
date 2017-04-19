//package edu.steward.login;
//
//import edu.steward.user.UserSession;
//import spark.Request;
//import spark.Response;
//import spark.Route;
//
//import java.util.HashMap;
//import java.util.Map;
//import com.google.gson.Gson;
//
///**
// * Created by kjin on 4/17/17.
// */
//public class Login {
//  private static final Gson GSON = new Gson();
//
//  public static Route loginPage = (Request request, Response response) -> {
//    Map<String, Object> res = new HashMap<>();
//    res.put("loggedOut", removeSessionAttrLoggedOut(request));
//    res.put("loginRedirect", removeSessionAttrLoginRedirect(request));
//    return GSON.toJson(res);
//  };
//
//
//  public static Route loginRequest = (Request request, Response response) -> {
//    Map<String, Object> res = new HashMap<>();
//    if (UserSession.authenticated(getQueryUsername(request), getQueryPassword
//        (request))) {
//      res.put("authentication", true);
//      request.session().attribute("user", getQueryUsername(request));
//      if (redirectPage(request) != null) {
//        response.redirect(redirectPage(request));
//      }
//      return GSON.toJson(res);
//    }
//    res.put("authentication", false);
//    return GSON.toJson(res);
//  };
//
//
//  public static Route logoutRequest = (Request request, Response response) -> {
//    request.session().removeAttribute("currentUser");
//    request.session().attribute("loggedOut", true);
//    response.redirect(a.Web.LOGIN);
//    return null;
//  };
//
//  public static void redirectToLogin(Request request, Response response) {
//    if (request.session().attribute("currentUser") == null) {
//      request.session().attribute("loginRedirect", request.pathInfo());
//      response.redirect(a.Web.LOGIN);
//    }
//  }
//
//
//
//}
