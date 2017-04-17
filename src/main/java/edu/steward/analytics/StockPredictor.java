package edu.steward.analytics;

import edu.steward.stock.Fundamental;
import edu.steward.stock.Price;

import java.util.List;

/**
 * Created by mrobins on 4/17/17.
 */
public interface StockPredictor {

  Price getFiveDayPrediction(String ticker);

  Price getOneDayPrediction(String ticker);

  List<Price> getPredictions(String ticker);
}
