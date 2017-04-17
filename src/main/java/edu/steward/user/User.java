package edu.steward.user;

import edu.steward.stock.Stock;

import java.util.List;

/**
 * Created by Philip on 4/16/17.
 */
public class User {
  private String username;
  private String password;
  private List<Stock> portfolio;
  private List<Stock> watchlist;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void setPortfolio(List<Stock> portfolio) {
    this.portfolio = portfolio;
  }

  public void setWatchlist(List<Stock> watchlist) {
    this.watchlist = watchlist;
  }
}
