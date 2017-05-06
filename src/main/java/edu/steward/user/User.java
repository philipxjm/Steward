package edu.steward.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.steward.pools.Pool;
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
    List<Portfolio> ret = DatabaseApi.getPortfoliosFromUser(this.getId());
    for (Portfolio port : ret) {
      portfolios.put(port.getName(), port);
    }
  }

  public List<Portfolio> getPoolPorts() {
    return DatabaseApi.getPoolsFromUser(this.getId());
  }

  public List<Portfolio> getPortfolios() {
    List<Portfolio> ret = new ArrayList<>();
    loadPortfolios();
    for (Portfolio port : portfolios.values()) {
      port.loadInfo();
      ret.add(port);
    }
    return ret;
  }

  public Portfolio getPortfolio(String name) {
    System.out.println(getId() + "/" + name);
    Portfolio port = DatabaseApi.getAllPorts(getId()).get(getId() + "/" + name);
    port.loadInfo();
    return port;
  }

  public boolean deletePortfolio(String name) {
    portfolios.remove(name);
    return DatabaseApi.removePortfolio(this.getId(), name);
  }

  public boolean renamePortfolio(String oldName, String newName) {
    if (portfolios.isEmpty()) {
      loadPortfolios();
    }
    if (DatabaseApi.renamePortfolio(this.getId(), oldName, newName)) {
      Portfolio old = portfolios.remove(oldName);
      old.setName(newName);
      portfolios.put(newName, old);
      return true;
    }
    return false;
  }

  public boolean addPortfolio(String portName) {
    System.out.println("Name: " + portName);
    if (portfolios.get(portName) == null) {
      boolean success = DatabaseApi.createPortfolio(getId(),
          getId() + "/" + portName, portName);
      if (!success) {
        return false;
      }
      Portfolio port = new Portfolio(portName, this.getId() + "/" + portName);
      port.setUser(getId());
      portfolios.put(portName, port);
      return true;

    }
    return false;
  }

  public Portfolio addPool(String poolId) {
    Pool pool = DatabaseApi.getPool(poolId);
    if (pool != null) {
      String portName = pool.getName();
      if (portfolios.get(portName) == null) {
        boolean success = DatabaseApi.createPortfolio(this.getId(),
            this.getId() + "/pool/" + portName, portName, pool.getBal());
        if (!success) {
          return null;
        }
        Portfolio port = new Portfolio(portName,
            this.getId() + "/pool/" + portName);
        port.joinPool(poolId);
        portfolios.put(portName, port);
        return port;
      }
    }
    return null;
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
