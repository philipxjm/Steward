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

  public void addHolding(Holding hold) {
    holdings.add(hold);
  }

  public String getName() {
    return name;
  }
}
