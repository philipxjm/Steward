package edu.steward.login;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;

/**
 * Created by kjin on 4/18/17.
 */
public class LoginConfigFactory implements ConfigFactory {

  public LoginConfigFactory() {
  }

  @Override
  public Config build() {
    final OidcConfiguration oidcConfiguration = new OidcConfiguration();
    oidcConfiguration.setClientId("993833341053-f3ks9cqj041b1uvj8an5omd7rdmu16j7.apps"
        + ".googleusercontent.com");
    oidcConfiguration.setSecret("d6dsR9McYEZgoRjTSbjv_VTJ");
    oidcConfiguration.setDiscoveryURI("https://accounts.google.com/.well-known/openid-configuration");
    oidcConfiguration.setUseNonce(true);
    //oidcClient.setPreferredJwsAlgorithm(JWSAlgorithm.RS256);
    oidcConfiguration.addCustomParam("prompt", "consent");
    final OidcClient oidcClient = new OidcClient(oidcConfiguration);

    final Google2Client googleClient = new Google2Client(
        "993833341053-f3ks9cqj041b1uvj8an5omd7rdmu16j7.apps"
            + ".googleusercontent.com", "d6dsR9McYEZgoRjTSbjv_VTJ");

    final Clients clients = new Clients("http://localhost:4567/callback",
        googleClient, oidcClient);

    final Config config = new Config(clients);
    config.setHttpActionAdapter(new HttpActionAdapter());
    return config;
  }
}