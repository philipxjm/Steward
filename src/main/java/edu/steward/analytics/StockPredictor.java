package edu.steward.analytics;

import edu.steward.stock.Fundamentals.Price;

import java.util.List;

/**
 * Created by mrobins on 4/17/17.
 */
public interface StockPredictor {

  Price getFiveDayPrediction(String ticker);
// TODO: ONE DAY PREDICTION
//  Price getOneDayPrediction(String ticker);

  List<Price> getPredictions(String ticker);
}
