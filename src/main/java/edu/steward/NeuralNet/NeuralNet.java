package edu.steward.NeuralNet;

import edu.steward.stock.Fundamentals.Price;

import java.util.List;

/**
 * Created by mrobins on 4/16/17.
 */
public interface NeuralNet {

//  TODO: Connect to neural network API
//  TODO: Create method for training
    void train(List<Price> companyPriceSeries, int epochs);
    Price run(List<Price> priceSeries, List<Integer> sentiments);
}
