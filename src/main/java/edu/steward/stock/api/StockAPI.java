package edu.steward.stock.api;

import edu.steward.stock.Fundamental;
import edu.steward.stock.Price;

import java.util.List;

/**
 * Created by mrobins on 4/17/17.
 */
public interface StockAPI {

  /**
   * Returns the data for a given stock.
   * @param ticker The unique ticker symbol for a stock.
   * @param startTime The unix start time from when to begin collecting data.
   * @param endTime The unix end time from when to finish collecting data.
   * @return A list of prices for the stock within the time frame.
   */
  List<Price> getStockPrices(String ticker, int startTime, int endTime);

  List<Fundamental> getStockFundamentals(String ticker, int startTime, int endTime);

  List<Fundamental> getGraphData(String ticker, int startTime, int endTime);

}
