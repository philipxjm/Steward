package edu.steward.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import org.eclipse.jetty.util.HostMap;

public class Portfolio {
  private String name;
  private String portfolioId;
  private Map<String, Holding> holdings;
  private Double balance;
//  TODO: Add in balance to the below methods

  public Portfolio(String name, String portfolioId) {
    this.name = name;
    this.portfolioId = portfolioId;
    holdings = new HashMap<>();
    // TODO: Load in holdings from db
  }

  public List<Holding> getHoldings() {
    List<Holding> ret = new ArrayList<>();
    for (
            String ticker : holdings.keySet()
            ) {
      ret.add(holdings.get(ticker));
    }
    return ret;
  }

  public boolean buyStock(String ticker, int shares, int time, double price) {
    // TODO
//    Holding h = holdings.get(ticker);
//    if (h != null) {
//      boolean b1 = (shares + h.getShares() > 0);
//      boolean b2 = ( )
//    }

    return UserData.stockTransaction(
            portfolioId,
            ticker,
            shares,
            time,
            price
    );
  }

  public boolean sellStock(String ticker, int shares, int time, double price) {
    // TODO
    return UserData.stockTransaction(
            portfolioId,
            ticker,
//            there is a negative sign here because it is SELL
            -shares,
            time,
            price
    );
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
