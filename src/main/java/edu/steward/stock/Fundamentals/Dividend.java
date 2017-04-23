package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class Dividend implements Fundamental<Dividend> {
  private double dividend;

  public Dividend(double dividend) {
    this.dividend = dividend;
  }

  @Override
  public Double getValue() {
    return dividend;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Dividend";
  }
}
