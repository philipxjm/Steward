package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class EPS extends Fundamental {
  private double eps;

  public EPS(double eps) {
    this.eps = eps;
  }

  @Override
  public Double getValue() {
    return eps;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "EPS";
  }
}
