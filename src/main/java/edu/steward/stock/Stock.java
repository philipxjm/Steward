package edu.steward.stock;

import java.util.List;

import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.StockAPI;

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

//  List<Price> getStockPrices(int startTime, int endTime) {
//    return stockAPI.getStockPrices(ticker, startTime, endTime);
//  }

  List<Fundamental> getStockFundamentals(StockAPI.TIMESERIES timeseries) {
    return stockAPI.getStockFundamentals(ticker, timeseries);
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

