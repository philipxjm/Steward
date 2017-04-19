package edu.steward.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mrobins on 4/17/17.
 */
public final class JSONRetriever {

  private JSONRetriever() {
  }

  /**
   * Gets JSON from a website.
   * @param url The url of the website.
   * @param timeout A timeoutVal value for establishing the connection and
   *                retrieving the JSON.
   * @return A String value of the JSON from the website is returned.
   */
  public static String getJSON(String url, int timeout) {
    HttpURLConnection conn = null;
    try {
      URL u = new URL(url);
      conn = (HttpURLConnection) u.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-length", "0");
      conn.setConnectTimeout(timeout);
      conn.setReadTimeout(timeout);
      conn.connect();
      int status = conn.getResponseCode();

      switch (status) {
        case 200:
          BufferedReader br = new BufferedReader(
                  new InputStreamReader(conn.getInputStream()));
          StringBuilder sb = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
          }
          br.close();
          return sb.toString();
        default:
          System.out.println("Error in establishing connection.");
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.disconnect();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
