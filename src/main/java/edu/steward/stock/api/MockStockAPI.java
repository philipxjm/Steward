package edu.steward.stock.api;

import java.util.List;

import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;

/**
 * Created by mrobins on 4/17/17.
 */
public class MockStockAPI implements StockAPI {

  // TODO: Make a mock stock api

  @Override
  public List<Price> getStockPrices(String ticker, TIMESERIES timeSeries) {
    return null;
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker) {
    return null;
  }

  @Override
  public Price getCurrPrice(String ticker) {
    return null;
  }

  @Override
  public DailyChange getDailyChange(String ticker) {
    return null;
  }

  @Override
  public Price getPrice(String ticker, int time) {
    return null;
  }

  @Override
  public String getCompanyName(String ticker) {
    return null;
  }

  //
  // @Override
  // public List<Fundamental> getGraphData(String ticker, TIMESERIES timeseries)
  // {
  // return null;
  // }
}
