package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class Average implements Fundamental<Average> {
  private double average;

  public Average(double average) {
    this.average = average;
  }

  @Override
  public Double getValue() {
    return average;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Average Volume";
  }
}
