package edu.steward.analytics;

import edu.steward.stock.Price;

import java.util.List;

/**
 * Created by mrobins on 4/17/17.
 */
public final class MockStockPredictor implements StockPredictor {

  public MockStockPredictor() {
  }

  public Price getFiveDayPrediction(String ticker) {
    return null;
  }

  public Price getOneDayPrediction(String ticker) {
    return null;
  }

  public List<Price> getPredictions(String ticker) {
    return null;
  }
}
