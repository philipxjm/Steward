package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class Ask extends Fundamental {
  private double ask;

  public Ask(double ask) {
    this.ask = ask;
  }

  @Override
  public Double getValue() {
    return ask;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Ask";
  }
}
