package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class BidSize implements Fundamental<BidSize> {
  private double bidSize;

  public BidSize(double bidSize) {
    this.bidSize = bidSize;
  }

  @Override
  public Double getValue() {
    return bidSize;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Bid Size";
  }
}
