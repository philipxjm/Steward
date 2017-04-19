package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/18/17.
 */
public class Volume implements Fundamental<Volume>{

  private double volume;
  private Long time;

  @Override
  public Double getValue() {
    return null;
  }

  @Override
  public Long getTime() {return (long) 0;}
}
