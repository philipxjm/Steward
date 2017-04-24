package edu.steward.user;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private String name;
  private String id;
  private List<Holding> holdings;

  public Portfolio(String name, String id) {
    this.name = name;
    this.id = id;
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
