package edu.steward.stock;

import java.util.List;

/**
 * Created by Philip on 4/16/17.
 */
public class Stock {


  private static StockAPI stockAPI;

  private String ticker;

  private List<Fundamental> stockData;

  public Stock(String ticker) {
    this.ticker = ticker;
  }

//  TODO: Add getters for fundamentals using the StockAPI

}
