package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class MarketCap implements Fundamental<MarketCap> {
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
  public String toString() {
    return "Market Cap";
  }
}

