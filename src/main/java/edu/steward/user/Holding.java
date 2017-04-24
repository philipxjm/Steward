package edu.steward.user;

public class Holding {
  private String ticker;
  private int shares;

  public Holding(String ticker, int shares) {
    this.ticker = ticker;
    this.shares = shares;
  }

  public String getTicker() {
    return ticker;
  }

  public int getShares() {
    return shares;
  }
}
