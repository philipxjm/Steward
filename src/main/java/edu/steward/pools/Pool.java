package edu.steward.pools;

import edu.steward.sql.DatabaseApi;
import edu.steward.user.Portfolio;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by kjin on 5/1/17.
 */
public class Pool {

  private List<Portfolio> portfolios;
  private String bal;
  private String start;
  private String id;
  private String name;
  private String end;

  public Pool (String name, String balance, String startTime, Portfolio...
      ports) {
    Random r = new Random();
    bal = balance;
    start = startTime;
    this.name = name;
    id = "";
    while (id.length() < 4 || DatabaseApi.getPool(id) != null) {
      id = id + Integer.toString(r.nextInt(36), 36);
    }
    System.out.println(id);
    portfolios = Arrays.asList(ports);
    DatabaseApi.initializePool(this);
  }

  public Pool (String id, String name, String balance, String startTime,
               Portfolio...
      ports) {
    this.id = id;
    bal = balance;
    start = startTime;
    this.name = name;
    portfolios = Arrays.asList(ports);
  }

  public void addPortfolio(Portfolio port) {
    portfolios.add(port);
  }

  public List<Portfolio> getPortfolios() {
    return portfolios;
  }

  public void setPortfolios(List<Portfolio> portfolios) {
    this.portfolios = portfolios;
  }

  public String getBal() {
    return bal;
  }

  public void setBal(String bal) {
    this.bal = bal;
  }

  public String getStart() {
    return start;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }
}
