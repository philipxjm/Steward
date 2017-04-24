package edu.steward.user;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private String name;
  private List<Holding> holdings;
  private Connection conn;
  private String uniqueId;

  public Portfolio(String name) {
    this.name = name;
    holdings = new ArrayList<>();
    // TODO: Load in holdings from db
  }

  public List<Holding> getHoldings() {
    if (holdings.isEmpty()) {
    }
    return holdings;
  }

  public void addHolding(Holding hold) {
    holdings.add(hold);
  }

  public String getName() {
    return name;
  }
}
