package edu.steward.stock.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.steward.stock.StockData;
import edu.steward.stock.Fundamentals.Ask;
import edu.steward.stock.Fundamentals.AskSize;
import edu.steward.stock.Fundamentals.Average;
import edu.steward.stock.Fundamentals.Bid;
import edu.steward.stock.Fundamentals.BidSize;
import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Dividend;
import edu.steward.stock.Fundamentals.EPS;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.MarketCap;
import edu.steward.stock.Fundamentals.PE;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.Fundamentals.Volume;
import edu.steward.stock.Fundamentals.YearHigh;
import edu.steward.stock.Fundamentals.YearLow;
import edu.steward.stock.Fundamentals.YieldPercent;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by mrobins on 4/20/17.
 */
public class YahooFinanceAPI implements StockAPI {

  private YahooFinance yahooFinance = new YahooFinance();

  @Override
  public List<Price> getStockPrices(String ticker, TIMESERIES timeSeries) {
    System.out.println("lkmlkm");
    return priceIntervalClean(StockData.getPrices(ticker), timeSeries);
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker) {
    try {
      List<Fundamental> ret = new ArrayList<>();
      System.out.println("ticker: " + ticker);
      Stock stock = YahooFinance.get(ticker);
      // If no stock exchange listed, presume it's a bad ticker and return null
      if (stock.getStockExchange() == null
          || stock.getStockExchange().equals("N/A")) {
        return null;
      }

      try {
        Ask ask = new Ask(stock.getQuote().getAsk().doubleValue());
        ret.add(ask);
      } catch (NullPointerException e) {
        // ask not found
      }

      try {
        AskSize askSize = new AskSize(stock.getQuote().getAskSize());
        ret.add(askSize);
      } catch (NullPointerException e) {
        // askSize not found
      }

      try {
        Average average = new Average(stock.getQuote().getAvgVolume());
        ret.add(average);
      } catch (NullPointerException e) {
        // average not found
      }

      try {
        Bid bid = new Bid(stock.getQuote().getBid().doubleValue());
        ret.add(bid);
      } catch (NullPointerException e) {

      }

      try {
        BidSize bidSize = new BidSize(stock.getQuote().getBidSize());
        ret.add(bidSize);
      } catch (NullPointerException e) {

      }

      try {
        Dividend dividend = new Dividend(
            stock.getDividend().getAnnualYield().doubleValue());
        ret.add(dividend);
      } catch (NullPointerException e) {

      }

      try {
        EPS eps = new EPS(stock.getStats().getEps().doubleValue());
        ret.add(eps);
      } catch (NullPointerException e) {

      }

      try {
        MarketCap marketCap = new MarketCap(
            stock.getStats().getMarketCap().doubleValue());
        ret.add(marketCap);
      } catch (NullPointerException e) {

      }

      try {
        PE pe = new PE(stock.getStats().getPe().doubleValue());
        ret.add(pe);
      } catch (NullPointerException e) {

      }

      try {
        Volume volume = new Volume(stock.getQuote().getVolume());
        ret.add(volume);
      } catch (NullPointerException e) {

      }

      try {
        YearHigh yearHigh = new YearHigh(
            stock.getQuote().getYearHigh().doubleValue());
        ret.add(yearHigh);
      } catch (NullPointerException e) {

      }

      try {
        YearLow yearLow = new YearLow(
            stock.getQuote().getYearLow().doubleValue());
        ret.add(yearLow);
      } catch (NullPointerException e) {

      }

      try {
        YieldPercent yieldPercent = new YieldPercent(
            stock.getDividend().getAnnualYieldPercent().doubleValue());
        ret.add(yieldPercent);
      } catch (NullPointerException e) {

      }

      return ret;
    } catch (IOException e) {
      return null;
    }
  }

  // public void func() {
  // Stock stock = YahooFinance.get("AAPL", true);
  //
  // // BigDecimal price = stock.getQuote().getPrice();
  // // BigDecimal change = stock.getQuote().getChangeInPercent();
  // // BigDecimal peg = stock.getStats().getPeg();
  // // BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
  //
  // System.out.println(stock.getHistory());
  // }

  public Price getCurrPrice(String ticker) {
    try {
      Stock stock = YahooFinance.get(ticker);
      Double priceValue = stock.getQuote().getPrice().doubleValue();
      return new Price(priceValue, System.currentTimeMillis() / 1000);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public DailyChange getDailyChange(String ticker) {
    try {
      Stock stock = YahooFinance.get(ticker);
      Double dailyChange = stock.getQuote().getChangeInPercent().doubleValue();
      return new DailyChange(dailyChange);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private List<Price> priceIntervalClean(List<Price> prices, TIMESERIES t) {
    int size = prices.size();
    List<Price> ret = new ArrayList<>();
    switch (t) {
    case SIX_MONTH:
      System.out.println("6 month");
      for (int i = 0; i < Math.min(26, size); i++) {
        ret.add(prices.get(i));
      }
      break;
    case ONE_YEAR:
      System.out.println("1 year");
      for (int i = 0; i < Math.min(52, size); i++) {
        ret.add(prices.get(i));
      }
      break;
    case TWO_YEAR:
      System.out.println("2 year");
      for (int i = 0; i < Math.min(104, size); i++) {
        ret.add(prices.get(i));
      }
      break;
    case FIVE_YEAR:
      System.out.println("5 year");
      for (int i = 0; i < Math.min(260, size); i++) {
        ret.add(prices.get(i));
      }
      break;
    case TEN_YEAR:
      System.out.println("10 year");
      for (int i = 0; i < Math.min(520, size); i++) {
        ret.add(prices.get(i));
      }
      break;
    }
    return ret;
  }

}
