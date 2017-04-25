package edu.steward.user;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private String name;
  private String portfolioId;
  private List<Holding> holdings;

  public Portfolio(String name, String portfolioId) {
    this.name = name;
    this.portfolioId = portfolioId;
    holdings = new ArrayList<>();
    // TODO: Load in holdings from db
  }

  public List<Holding> getHoldings() {
    return holdings;
  }

  public boolean buyStock(String ticker, int shares) {
    // TODO
    return true;
  }

  public boolean sellStock(String ticker, int shares) {
    // TODO
    return true;
  }

  public String getName() {
    return name;
  }
}
