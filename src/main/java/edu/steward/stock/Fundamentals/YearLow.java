package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class YearLow implements Fundamental<YearLow>{
    private double low;

    public YearLow(double low) {
      this.low = low;
    }

    @Override
    public Double getValue() {
      return low;
    }

    @Override
    public Long getTime() {
      return System.currentTimeMillis() / 1000;
    }

  @Override
  public String getType() {
    return "52 Week Low";
  }

}
