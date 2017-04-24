package edu.steward.stock.Fundamentals;

/**
 * Created by mrobins on 4/17/17.
 */
public class Price extends Fundamental {

  private enum TYPE {
    OPEN, CLOSE, HIGH, LOW
  }

  private Double price;
  private Long time;

  public Price(Double price, Long time) {
    this.price = price;
    this.time = time;
  }

  public Double getValue() {
    return price;
  }

  public Long getTime() {
    return time;
  }

  @Override
  public String getType() {
    return "Price";
  }
}
