package edu.steward.pools;

import edu.steward.sql.DatabaseApi;
import edu.steward.user.Portfolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kjin on 5/1/17.
 */
public class Pool {

  private List<Portfolio> portfolios;
  private String bal;
  private String start;
  private String name;

  public Pool (String balance, String startTime, String name, Portfolio...
      ports) {
    bal = balance;
    start = startTime;
    portfolios = Arrays.asList(ports);
    DatabaseApi.initializePool(this);
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
}
