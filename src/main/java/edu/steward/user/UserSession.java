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

  private String sessionId;
  private String id;
  private String name;

  UserSession(String name, String id) {
    this.name = name;
    this.id = id;
    this.sessionId = name + id;
  }

  public static ModelAndView destPage(final Request request, final Response
      response) {

    Map<String, String> variables = ImmutableMap.of("title", "", "ticker",
        "placeholder",
        "user", "John Smith");
    return new ModelAndView(variables, "stock.ftl");
  }

  private static List<CommonProfile> getProfiles(final Request request, final Response response) {
    final SparkWebContext context = new SparkWebContext(request, response);
    final ProfileManager manager = new ProfileManager(context);
    return manager.getAll(true);
  }

  public static ModelAndView newSession(final Request
                                            request, final Response
                                            response) {

    QueryParamsMap qm = request.queryMap();

    UserSession ns = new UserSession(qm.value("name"),
        qm.value("id"));

    Map<String, String> variables = ImmutableMap.of("title", "", "ticker",
        "placeholder",
        "user", "John Smith");
    return new ModelAndView(variables, "stock.ftl");
  }
}
