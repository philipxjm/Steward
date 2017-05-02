package edu.steward.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import edu.steward.sql.DatabaseApi;

public class Portfolio {
  private String name;
  private String portfolioId;
  private Map<String, Integer> holdings;
  private Double balance = 1000.0;
  // TODO: Add in balance to the below methods

  public Portfolio(String name, String portfolioId) {
    this.name = name;
    this.portfolioId = portfolioId;
    // TODO: Load in holdings from db
    loadInfo();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void loadInfo() {
    holdings = DatabaseApi.getStocksFromPortfolio(portfolioId);
    balance = DatabaseApi.getBalanceFromPortfolio(portfolioId);
  }

  public Map<String, Integer> getHoldings() {
    if (holdings == null) {
      loadInfo();
    }
    return holdings;
  }

  public boolean buyStock(String ticker, int shares, int time, double price) {
    // TODO
    double cost = price * shares;
    if (cost > balance) {
      return false;
    } else {
      Integer currShares = holdings.get(ticker);
      Integer newShares;
      if (currShares == null) {
        newShares = shares;
      } else {
        newShares = shares + currShares;
      }

      holdings.replace(ticker, newShares);
      balance -= cost;

      // TODO: this should add the transaction to history and change the balance
      return DatabaseApi.stockTransaction(portfolioId, ticker, shares, time,
          price);
    }
  }

  public boolean sellStock(String ticker, int shares, int time, double price) {
    // TODO
    double cost = price * shares;
    Integer currShares = holdings.get(ticker);
    if (currShares == null) {
      return false;
    } else if (currShares < shares) {
      return false;
    } else {
      holdings.replace(ticker, currShares - shares);
      balance += cost;
      return DatabaseApi.stockTransaction(portfolioId, ticker,
          // there is a negative sign here because it is SELL
          -shares, time, price);
    }
  }

  public String getName() {
    return name;
  }

  public List<List<Double>> getUnrealized() {
    // TODO Implement, this is mock
    // Also should probably use a class rather than List<Double>
    List<List<Double>> ret = new ArrayList<>();
    double time = 0;
    int endTime = 100;
    double val = 0;
    ret.add(ImmutableList.of(time, val));
    for (; time < endTime; time++) {
      val += ((Math.random() * 11) - 5);
      ret.add(ImmutableList.of(time, val));
    }
    return ret;
  }
}
