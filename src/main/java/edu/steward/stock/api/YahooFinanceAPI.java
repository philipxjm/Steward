package edu.steward.stock.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import edu.steward.sql.DatabaseApi;
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
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by mrobins on 4/20/17.
 */
public class YahooFinanceAPI implements StockAPI {

  public YahooFinanceAPI() {
    YahooFinance.logger.setLevel(Level.OFF);
  }

  @Override
  public List<Price> getStockPrices(String ticker, TIMESERIES timeSeries) {
    return priceIntervalClean(DatabaseApi.getPrices(ticker), timeSeries);
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker) {
    try {
      List<Fundamental> ret = new ArrayList<>();
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

  public String getCompanyName(String ticker) {
    try {
      Stock s = YahooFinance.get(ticker);
      String name = s.getName();
      return name;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Price getCurrPrice(String ticker) {
    try {
      Stock stock = YahooFinance.get(ticker);
      BigDecimal quote = stock.getQuote().getPrice();
      if (quote == null) {
        return null;
      }
      Double priceValue = quote.doubleValue();
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
    case ONE_MONTH:
      // ~23 days of daily data
      for (int i = 0; i < Math.min(23, size); i += 1) {
        ret.add(prices.get(i));
      }
      break;
    case SIX_MONTH:
      // ~130 days of daily data
      for (int i = 0; i < Math.min(130, size); i += 2) {
        ret.add(prices.get(i));
      }
      break;
    case ONE_YEAR:
      // ~260 days of daily data
      for (int i = 0; i < Math.min(260, size); i += 3) {
        ret.add(prices.get(i));
      }
      break;
    case TWO_YEAR:
      // ~520 days of daily data
      for (int i = 0; i < Math.min(520, size); i += 5) {
        ret.add(prices.get(i));
      }
      break;
    case FIVE_YEAR:
      // ~1300 days of daily data
      for (int i = 0; i < Math.min(1300, size); i += 10) {
        ret.add(prices.get(i));
      }
      break;
    case TEN_YEAR:
      // ~2600 days of daily data
      for (int i = 0; i < Math.min(2600, size); i += 10) {
        ret.add(prices.get(i));
      }
      break;
    }
    return ret;
  }

  @Override
  public Price getPrice(String ticker, int time) {
    Calendar from = Calendar.getInstance();
    from.setTimeInMillis(1000L * (long) time - 100000L);
    Calendar to = Calendar.getInstance();
    to.setTimeInMillis(1000L * (long) time);
    try {
      Stock stock = YahooFinance.get(ticker, true);
      List<HistoricalQuote> quotes = stock.getHistory(from, to, Interval.DAILY);
      return new Price(quotes.get(0).getAdjClose().doubleValue(), (long) time);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Stock not found");
      return null;
    }
  }
}
