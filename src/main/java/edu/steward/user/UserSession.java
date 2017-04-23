package edu.steward.user;

import com.google.common.collect.ImmutableMap;
import edu.steward.handlers.IndexHandler;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.sparkjava.SparkWebContext;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrobins on 4/16/17.
 */
public class UserSession {

//  TODO: Implement spark pac4j for secure login/logout

  public static ModelAndView destPage(final Request request, final Response
      response) {

    Map<String, String> variables = ImmutableMap.of("title", "", "ticker",
        "placeholder",
        "user", "John Smith");
    return new ModelAndView(variables, "stock.ftl");
  }
}
