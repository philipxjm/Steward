package edu.steward.user;

public class Holding implements Comparable<Holding>{
  private String ticker;
  private int shares;
  private long time;
  private double price;

  public Holding(String ticker, int shares, long time) {
    this.ticker = ticker;
    this.shares = shares;
    this.time = time;
  }

  public Holding(String ticker, int shares, long time, double price) {
    this.ticker = ticker;
    this.shares = shares;
    this.time = time;
    this.price = price;
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

  public double getPrice() {
    return price;
  }

  public long getTime() {
    return time;
  }

  @Override
  public int compareTo(Holding o) {
    return Long.compare(time, o.time);
  }
}
