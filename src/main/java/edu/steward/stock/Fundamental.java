package edu.steward.stock;

/**
 * Created by mrobins on 4/16/17.
 */
public class Fundamental {

  private String ticker;
  private String type;
  private Double value;

  public Fundamental(String ticker, String type, Double value) {
    this.ticker = ticker;
    this.type = type;
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public Double getValue() {
    return value;
  }

  public String getTicker() {
    return ticker;
  }
}
