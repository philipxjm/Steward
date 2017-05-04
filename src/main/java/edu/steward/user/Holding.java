package edu.steward.user;

public class Holding implements Comparable<Holding>{
  private String ticker;
  private int shares;
  private int time;

  public Holding(String ticker, int shares, int time) {
    this.ticker = ticker;
    this.shares = shares;
    this.time = time;
  }

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

  public int getTime() {
    return time;
  }

  @Override
  public int compareTo(Holding o) {
    return time - o.time;
  }
}
