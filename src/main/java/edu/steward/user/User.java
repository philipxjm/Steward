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
    Portfolio port = new Portfolio("default", "");
    port.addHolding(new Holding("FB", 12));
    port.addHolding(new Holding("SNAP", 43));
    portfolios.put("default", port);
    port = new Portfolio("test", "");
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
    if (portfolios.isEmpty()) {
      ret = UserData.getPortfoliosFromUser(this.getId());
      for (Portfolio port : ret) {
        portfolios.put(port.getName(), port);
      }
      return ret;
    }
    for (Portfolio port : portfolios.values()) {
      ret.add(port);
    }
    return ret;
  }

  public Portfolio getPortfolio(String name) {
    return portfolios.get(name);
  }

  public boolean addPortfolio(String portName) {
    if (portfolios.get(portName) == null) {
      portfolios.put(portName, new Portfolio(portName, this.getId() + "/" +
          portName));
      UserData.createPortfolio(this.getId(), portName);
      return true;
    }
    return false;
  }

  public boolean removePortfolio(String portName) {
    Portfolio port = portfolios.get(portName);
    if (port == null) {
      return false;
    }
    portfolios.remove(portName);
    UserData.removePortfolio(this.getId(), portName);
    return true;
  }
}
