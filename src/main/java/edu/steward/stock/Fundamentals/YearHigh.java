package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class YearHigh implements Fundamental<YearHigh>{
  private double high;

  public YearHigh(double high) {
    this.high = high;
  }

  @Override
  public Double getValue() {
    return high;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String toString() {
    return "52 Week High";
  }
}
