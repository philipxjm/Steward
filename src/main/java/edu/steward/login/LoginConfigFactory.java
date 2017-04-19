package edu.steward.login;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.oauth.client.Google2Client;
import spark.TemplateEngine;

/**
 * Created by kjin on 4/18/17.
 */
public class LoginConfigFactory implements ConfigFactory {

  public LoginConfigFactory() {}

  @Override
  public Config build() {
    final Google2Client googleClient = new Google2Client(
        "993833341053-f3ks9cqj041b1uvj8an5omd7rdmu16j7.apps"
            + ".googleusercontent.com", "d6dsR9McYEZgoRjTSbjv_VTJ");

    final Clients clients = new Clients("http://localhost:8080/callback",
        googleClient);

    final Config config = new Config(clients);
    return config;
  }
}