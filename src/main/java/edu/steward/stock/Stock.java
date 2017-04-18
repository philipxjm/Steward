package edu.steward.stock;

import edu.steward.stock.StockAPI.StockAPI;

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

  List<Price> getStockPrices(int startTime, int endTime) {
    return stockAPI.getStockPrices(ticker, startTime, endTime);
  }

  List<Fundamental> getStockFundamentals(int startTime, int endTime) {
    return stockAPI.getStockFundamentals(ticker, startTime, endTime);
  }

  public static void setStockAPI(StockAPI stockAPI) {
    Stock.stockAPI = stockAPI;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (!(o instanceof Stock)) {
      return false;
    }

    Stock s = (Stock) o;

    return ticker.equals(s.ticker);
  }

  @Override
  public int hashCode() {
    return ticker.hashCode();
  }
}

