package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/22/17.
 */
public class DailyChange extends Fundamental {
  private double dailyChange;

  public DailyChange(double dailyChange) {
    this.dailyChange = dailyChange;
  }

  @Override
  public Double getValue() {
    return dailyChange;
  }

  @Override
  public Long getTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public String getType() {
    return "Daily Change";
  }

  @Override
  public String toString() {
    return "DailyChange{" + "dailyChange=" + dailyChange + '}';
  }
}
