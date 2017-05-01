package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/16/17.
 */
public abstract class Fundamental {
  public abstract Double getValue();

  public abstract Long getTime();

  public abstract String getType();

  public String getNiceValue() {
    if (getValue() > 1_000_000_000) {
      return String.format("%.2fB", getValue() / 1_000_000_000);
    } else if (getValue() > 1_000_000) {
      return String.format("%.2fM", getValue() / 1_000_000);
    } else if (Math.floor(getValue()) == getValue()) {
      return getValue().intValue() + "";
    } else
      return getValue().toString();
  }
}
