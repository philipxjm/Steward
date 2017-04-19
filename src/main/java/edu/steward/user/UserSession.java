package edu.steward.user;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.sparkjava.SparkWebContext;
import spark.ModelAndView;
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
    final Map map = new HashMap();
    map.put("profiles", getProfiles(request, response));
    return new ModelAndView(map, "index.html");
  }

  private static List<CommonProfile> getProfiles(final Request request, final Response response) {
    final SparkWebContext context = new SparkWebContext(request, response);
    final ProfileManager manager = new ProfileManager(context);
    return manager.getAll(true);
  }
}
