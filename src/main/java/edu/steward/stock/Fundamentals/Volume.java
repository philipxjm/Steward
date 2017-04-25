package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/18/17.
 */
public class Volume extends Fundamental {

  private double volume;
  private Long time;

  public Volume(double volume) {
    this.volume = volume;
  }

  @Override
  public Double getValue() {
    return volume;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Volume";
  }
}
