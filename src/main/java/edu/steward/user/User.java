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
  }

  public String getId() {
    return hashedId;
  }

  public void loadPortfolios() {
    List<Portfolio> ret = UserData.getPortfoliosFromUser(this.getId());
    for (Portfolio port : ret) {
      portfolios.put(port.getName(), port);
    }
    System.out.println(ret);
  }

  public List<Portfolio> getPortfolios() {
    List<Portfolio> ret = new ArrayList<>();
    if (portfolios.isEmpty()) {
      loadPortfolios();
    }
    for (Portfolio port : portfolios.values()) {
      ret.add(port);
    }
    return ret;
  }

  public Portfolio getPortfolio(String name) {
    if (portfolios.isEmpty()) {
      loadPortfolios();
    }
    return portfolios.get(name);
  }

  public boolean addPortfolio(String portName) {
    if (portfolios.get(portName) == null) {
      portfolios.put(portName,
          new Portfolio(portName, this.getId() + "/" + portName));
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
