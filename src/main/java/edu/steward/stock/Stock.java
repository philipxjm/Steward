package edu.steward.stock;

import java.util.ArrayList;
import java.util.List;

import edu.steward.sql.DailyDataUpdater;
import edu.steward.sql.DatabaseApi;
import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.AlphaVantageAPI;
import edu.steward.stock.api.StockAPI;
import edu.steward.stock.api.YahooFinanceAPI;

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

  // TODO: Add getters for fundamentals using the StockAPI
  public List<Price> getStockPrices(StockAPI.TIMESERIES timeseries) {
    // TODO: Abstract this later on
    if (timeseries == StockAPI.TIMESERIES.ONE_DAY) {
      return DailyDataUpdater.initializeIfEmptyFive(ticker);
    } else if (timeseries == StockAPI.TIMESERIES.FIVE_DAY) {
      return DailyDataUpdater.initializeIfEmptyThirty(ticker);
    } else {
      try {
        setStockAPI(new YahooFinanceAPI());
        List<Price> prices = stockAPI.getStockPrices(ticker, timeseries);
        return prices;
      } catch (NullPointerException e) {
        setStockAPI(new AlphaVantageAPI());
        return stockAPI.getStockPrices(ticker, timeseries);
      }
    }
  }

  public Price getCurrPrice() {
    setStockAPI(new YahooFinanceAPI());
    return stockAPI.getCurrPrice(ticker);
  }

  public Price getPrice(int time) {
    return DatabaseApi.getPrice(ticker, time);
  }

  public DailyChange getDailyChange() {
    setStockAPI(new YahooFinanceAPI());
    return stockAPI.getDailyChange(ticker);
  }

  public String getCompanyName() {
    setStockAPI(new YahooFinanceAPI());
    return stockAPI.getCompanyName(ticker);
  }

  public List<Fundamental> getStockFundamentals() {
    setStockAPI(new YahooFinanceAPI());
    List<Fundamental> ret = new ArrayList<>();
    return stockAPI.getStockFundamentals(ticker);
  }

  public static void setStockAPI(StockAPI stockAPI) {
    Stock.stockAPI = stockAPI;
  }

  public String getTicker() {
    return ticker;
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
