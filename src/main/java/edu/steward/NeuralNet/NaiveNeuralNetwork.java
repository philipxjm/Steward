package edu.steward.NeuralNet;

import edu.steward.stock.Fundamentals.Price;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Created by Philip on 4/19/2017.
 */
public class NaiveNeuralNetwork implements NeuralNet {
    @Override
    public double train(List<List<Price>> priceSeries) {
        return 0;
    }

    @Override
    public double run(List<Price> prices, List<Integer> sentiments) {
        double pAverage = (prices.get(0).getValue()
                + prices.get(prices.size() - 1).getValue())
                / 2.0;
        OptionalDouble sAverage = sentiments
                .stream()
                .mapToDouble(a -> a)
                .average();
        if (sAverage.isPresent()) {
            if (sAverage.getAsDouble() > 1) {
                return pAverage + ((prices.get(prices.size() - 1).getValue()
                        - prices.get(0).getValue()) / (double) prices.size());
            } else {
                return pAverage - ((prices.get(prices.size() - 1).getValue()
                        - prices.get(0).getValue()) / (double) prices.size());
            }
        } else {
            return pAverage;
        }
    }
}
