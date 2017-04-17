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

  public List<Stock> getPortfolio() {
    return portfolio;
  }

  public List<Stock> getWatchlist() {
    return watchlist;
  }

  public boolean addStockToPortfolio(String ticker) {
    Stock toBeAdded = new Stock(ticker);
    if (!portfolio.contains(ticker)) {
      portfolio.add(toBeAdded);
      return true;
    } else {
      return false;
    }
  }

  public boolean removeStockFromPortfolio(String ticker) {
    Stock toBeRemoved = new Stock(ticker);
    if (portfolio.contains(toBeRemoved)) {
      portfolio.remove(toBeRemoved);
      return true;
    } else {
      return false;
    }
  }

  public boolean addStockToWatchlist(String ticker) {
    Stock toBeAdded = new Stock(ticker);
    if (!watchlist.contains(ticker)) {
      watchlist.add(toBeAdded);
      return true;
    } else {
      return false;
    }
  }

  public boolean removeStockFromWatchlist(String ticker) {
    Stock toBeRemoved = new Stock(ticker);
    if (watchlist.contains(toBeRemoved)) {
      watchlist.remove(toBeRemoved);
      return true;
    } else {
      return false;
    }
  }

}
