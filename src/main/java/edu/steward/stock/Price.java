package edu.steward.stock;

/**
 * Created by mrobins on 4/17/17.
 */
public class Price implements Fundamental<Price> {

  private Double price;
  private int time;

  public Price(Double price, int time) {
    this.price = price;
    this.time = time;
  }

  public Double getValue() {
    return null;
  }

  public int getTime() {
    return time;
  }
}
