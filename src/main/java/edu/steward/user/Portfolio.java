package edu.steward.user;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

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
