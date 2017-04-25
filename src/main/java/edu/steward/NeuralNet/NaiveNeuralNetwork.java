//package edu.steward.NeuralNet;
//
//import edu.steward.stock.Fundamentals.Price;
//
//import java.util.List;
//import java.util.OptionalDouble;
//
///**
// * Created by Philip on 4/19/2017.
// */
//public class NaiveNeuralNetwork implements NeuralNet {
//  @Override
//  public void train(List<Price> priceSeries) {
//    return;
//  }
//
//  @Override
//  public Price run(List<Price> prices, List<Integer> sentiments) {
//    double pAverage = (prices.get(0).getValue()
//            + prices.get(prices.size() - 1).getValue())
//            / 2.0;
//    OptionalDouble sAverage = sentiments
//            .stream()
//            .mapToDouble(a -> a)
//            .average();
//    if (sAverage.isPresent()) {
//      if (sAverage.getAsDouble() > 1) {
//        return new Price(pAverage + ((prices.get(prices.size() - 1).getValue()
//                - prices.get(0).getValue()) / (double) prices.size()),
//                System.currentTimeMillis() / 1000 + 432000);
//      } else {
//        return new Price(pAverage - ((prices.get(prices.size() - 1).getValue()
//                - prices.get(0).getValue()) / (double) prices.size()),
//                System.currentTimeMillis() / 1000 + 432000);
//      }
//    } else {
//      return new Price(pAverage, System.currentTimeMillis() / 1000 + 432000);
//    }
//  }
//}
