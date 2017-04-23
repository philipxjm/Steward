package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class Bid implements Fundamental<Bid> {
  private double bid;

  public Bid(double bid) {
    this.bid = bid;
  }

  @Override
  public Double getValue() {
    return bid;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Bid";
  }
}
