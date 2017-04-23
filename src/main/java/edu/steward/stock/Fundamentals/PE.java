package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class PE implements Fundamental<PE> {
  private double pe;

  public PE(double pe) {
    this.pe = pe;
  }

  @Override
  public Double getValue() {
    return pe;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "PE";
  }
}
