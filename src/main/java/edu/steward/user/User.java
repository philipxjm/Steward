package edu.steward.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Philip on 4/16/17.
 */
public class User {
  private Map<String, Portfolio> portfolios;
  private String hashedId;

  public User(String id) {
    hashedId = id;
    portfolios = new HashMap<>();
    // This is mock
    Portfolio port = new Portfolio("default");
    port.addHolding(new Holding("FB", 12));
    port.addHolding(new Holding("SNAP", 43));
    portfolios.put("default", port);
    port = new Portfolio("test");
    port.addHolding(new Holding("AAPL", 10));
    port.addHolding(new Holding("TSLA", 23));
    portfolios.put("test", port);
    // TODO: Load in portfolios from db
  }

  public String getId() {
    return hashedId;
  }

  public List<Portfolio> getPortfolios() {
    List<Portfolio> ret = new ArrayList<>();
    for (Portfolio port : portfolios.values()) {
      ret.add(port);
    }
    return ret;
  }

  public Portfolio getPortfolio(String name) {
    return portfolios.get(name);
  }

  public void addPorfolio(Portfolio port) {
    portfolios.put(port.getName(), port);
  }
}
