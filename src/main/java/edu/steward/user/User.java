package edu.steward.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.steward.sql.DatabaseApi;

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
    System.out.println("load portfolios called");
    List<Portfolio> ret = DatabaseApi.getPortfoliosFromUser(this.getId());
    for (Portfolio port : ret) {
      portfolios.put(port.getName(), port);
    }
  }

  public List<Portfolio> getPortfolios() {
    List<Portfolio> ret = new ArrayList<>();
    if (portfolios.isEmpty()) {
      loadPortfolios();
    }
    for (Portfolio port : portfolios.values()) {
      port.loadInfo();
      ret.add(port);
    }
    return ret;
  }

  public Portfolio getPortfolio(String name) {
    if (portfolios.isEmpty()) {
      loadPortfolios();
    }
    System.out.println(portfolios);
    portfolios.get(name).loadInfo();
    return portfolios.get(name);
  }

  public boolean addPortfolio(String portName) {
    System.out.println(portName);
    if (portfolios.get(portName) == null) {
      boolean success = DatabaseApi.createPortfolio(this.getId(), portName);
      if (!success) {
        return false;
      }
      portfolios.put(portName,
          new Portfolio(portName, this.getId() + "/" + portName));
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
    DatabaseApi.removePortfolio(this.getId(), portName);
    return true;
  }
}
