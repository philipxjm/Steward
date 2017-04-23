package edu.steward.stock.api;

import edu.steward.stock.Fundamentals.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrobins on 4/20/17.
 */
public class YahooFinanceAPI implements StockAPI {

  private YahooFinance yahooFinance = new YahooFinance();

  @Override
  public List<Price> getStockPrices(String ticker, TIMESERIES timeSeries) {
    return null;
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker) {
    List<Fundamental> ret = new ArrayList<>();
    Stock stock = YahooFinance.get(ticker);

    Ask ask = new Ask(stock.getQuote().getAsk().doubleValue());
    ret.add(ask);

    AskSize askSize = new AskSize(stock.getQuote().getAskSize());
    ret.add(askSize);

    Average average = new Average(stock.getQuote().getAvgVolume());
    ret.add(average);

    Bid bid = new Bid(stock.getQuote().getBid().doubleValue());
    ret.add(bid);

    BidSize bidSize = new BidSize(stock.getQuote().getBidSize());
    ret.add(bidSize);

    Dividend dividend = new Dividend(stock.getDividend().getAnnualYield().doubleValue());
    ret.add(dividend);

    EPS eps = new EPS(stock.getStats().getEps().doubleValue());
    ret.add(eps);

    MarketCap marketCap = new MarketCap(stock.getStats().getMarketCap().doubleValue());
    ret.add(marketCap);

    PE pe = new PE(stock.getStats().getPe().doubleValue());
    ret.add(pe);

    Volume volume = new Volume(stock.getQuote().getVolume());
    ret.add(volume);

    YearHigh yearHigh = new YearHigh(stock.getQuote().getYearHigh().doubleValue());
    ret.add(yearHigh);

    YearLow yearLow = new YearLow(stock.getQuote().getYearLow().doubleValue());
    ret.add(yearLow);

    YieldPercent yieldPercent = new YieldPercent(stock.getDividend().getAnnualYieldPercent().doubleValue());
    ret.add(yieldPercent);

    return ret;
  }

  public void func() {
    Stock stock = YahooFinance.get("AAPL", true);

//    BigDecimal price = stock.getQuote().getPrice();
//    BigDecimal change = stock.getQuote().getChangeInPercent();
//    BigDecimal peg = stock.getStats().getPeg();
//    BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

    System.out.println(stock.getHistory());
  }

  public Price getCurrPrice(String ticker) {
    Stock stock = new Stock(ticker);
    Double priceValue = stock.getQuote().getPrice().doubleValue();
    return new Price(priceValue, System.currentTimeMillis() / 1000);
  }

  public DailyChange getDailyChange(String ticker) {
    Stock stock = new Stock(ticker);
    Double dailyChange = stock.getQuote().getChangeInPercent().doubleValue();
    return new DailyChange(dailyChange);
  }

}
