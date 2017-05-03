package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 5/2/17.
 */
public class Gains extends Fundamental {

  private Double gains;
  private Long time;

  public Gains(Double gains, Long time) {
    this.gains = gains;
    this.time = time;
  }

  @Override
  public Double getValue() {
    return gains;
  }

  @Override
  public Long getTime() {
    return time;
  }

  @Override
  public String getType() {
    return null;
  }
}
