package edu.steward.ai.strategies;

/**
 * Created by Philip on 5/5/17.
 */
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Rule;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.oscillators.StochasticOscillatorKIndicator;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.EMAIndicator;
import eu.verdelhan.ta4j.indicators.trackers.MACDIndicator;
import eu.verdelhan.ta4j.trading.rules.CrossedDownIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.CrossedUpIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.OverIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.UnderIndicatorRule;

public class MovingMomentum {
  /**
   * @param series a time series
   * @return a moving momentum strategy
   */
  public static Strategy buildStrategy(TimeSeries series) {
    if (series == null) {
      throw new IllegalArgumentException("Series cannot be null");
    }

    ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

    // The bias is bullish when the shorter-moving average moves above the longer moving average.
    // The bias is bearish when the shorter-moving average moves below the longer moving average.
    EMAIndicator shortEma = new EMAIndicator(closePrice, 9);
    EMAIndicator longEma = new EMAIndicator(closePrice, 26);

    StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(series, 14);

    MACDIndicator macd = new MACDIndicator(closePrice, 9, 26);
    EMAIndicator emaMacd = new EMAIndicator(macd, 18);

    // Entry rule
    Rule entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
            .and(new CrossedDownIndicatorRule(stochasticOscillK, Decimal.valueOf(20))) // Signal 1
            .and(new OverIndicatorRule(macd, emaMacd)); // Signal 2

    // Exit rule
    Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
            .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(80))) // Signal 1
            .and(new UnderIndicatorRule(macd, emaMacd)); // Signal 2

    return new Strategy(entryRule, exitRule);
  }
}
