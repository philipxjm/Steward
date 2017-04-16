package edu.steward.analytics;

import java.util.Collections;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Philip on 4/16/17.
 */
public final class TwitterSearch {
  private static String consumerKey = "9xI8528t1ZZZA2hXXnUHQUcuo";
  private static String consumerSecret = "XcG9waggg0cwP0julXf6GMDoHOuH8y1lfR5Sfu4H7QBCslzdIR";
  private static String accessToken = "2155608048-PQ6Y8lpWNFl1fee17cjP4gBjt6QiDOiqVqtyCYX";
  private static String accessTokenSecret = "865O8TfVFOGjl7lQTMuubru690ETdwJ5y7ftE0gAkUkkI";

  private TwitterSearch() {
    // not called.
  }

  public static void setConsumerKey(String consumerKey) {
    TwitterSearch.consumerKey = consumerKey;
  }

  public static void setConsumerSecret(String consumerSecret) {
    TwitterSearch.consumerSecret = consumerSecret;
  }

  public static void setAccessToken(String accessToken) {
    TwitterSearch.accessToken = accessToken;
  }

  public static void setAccessTokenSecret(String accessTokenSecret) {
    TwitterSearch.accessTokenSecret = accessTokenSecret;
  }

  public static List<Status> search(String keyword) {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret);
    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();
    Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
    query.setCount(20);
    query.setLocale("en");
    query.setLang("en");
    try {
      QueryResult queryResult = twitter.search(query);
      return queryResult.getTweets();
    } catch (TwitterException e) {
      // ignore
      e.printStackTrace();
    }
    return Collections.emptyList();
  }
}
