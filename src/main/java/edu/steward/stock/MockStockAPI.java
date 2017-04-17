package edu.steward.stock;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mrobins on 4/17/17.
 */
public class MockStockAPI implements StockAPI {

  //TODO: Make a mock stock api

  @Override
  public List<Price> getStockPrices(String ticker, int startTime, int endTime) {
    List<Price> ret = new ArrayList<>();
    double pVal = 50;
    double inter = (endTime - startTime) / 100.0;

    for (int i = startTime; i <= endTime; i += inter) {
      double newPVal = pVal + Math.random() * 10 - 5;
      Price price = new Price(newPVal, i);
      ret.add(price);
      pVal = newPVal;
    }
    return ret;
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker, int startTime, int endTime) {
    return null;
  }

  @Override
  public List<Fundamental> getGraphData(String ticker, int startTime, int endTime) {
    return null;
  }

}
