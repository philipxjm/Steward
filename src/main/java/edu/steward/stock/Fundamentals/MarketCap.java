package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class MarketCap extends Fundamental {
  private double mc;

  public MarketCap(double mc) {
    this.mc = mc;
  }

  @Override
  public Double getValue() {
    return mc;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Market Cap";
  }
}
