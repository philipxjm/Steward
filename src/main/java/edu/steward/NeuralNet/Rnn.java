package edu.steward.NeuralNet;

import edu.steward.stock.Fundamentals.Price;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Philip on 5/7/2017.
 */
public class Rnn implements NeuralNet{
  private int numInputs;
  private int numHiddenLayers;
  private int numHiddenNeurons;
  private MultiLayerNetwork net;

  public Rnn(int numInputs, int numHiddenLayers) {
    this.numInputs = numInputs;
    this.numHiddenLayers = numHiddenLayers;
    this.numHiddenNeurons = 2 * numInputs + 1;

    NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();
    builder.iterations(100);
    builder.learningRate(0.001);
    builder.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT);
    builder.seed(420);
    builder.biasInit(0);
    builder.miniBatch(false);
    builder.updater(Updater.RMSPROP);
    builder.weightInit(WeightInit.XAVIER);

    ListBuilder listBuilder = builder.list();

    for (int i = 0; i < numHiddenLayers; i++) {
      GravesLSTM.Builder hiddenLayerBuilder = new GravesLSTM.Builder();
      hiddenLayerBuilder.nIn(i == 0 ? numInputs : numHiddenNeurons);
      hiddenLayerBuilder.nOut(numHiddenNeurons);

      hiddenLayerBuilder.activation(Activation.TANH);
      listBuilder.layer(i, hiddenLayerBuilder.build());
    }

    RnnOutputLayer.Builder outputLayerBuilder = new RnnOutputLayer.Builder(LossFunction.MCXENT);
    outputLayerBuilder.activation(Activation.SOFTMAX);
    outputLayerBuilder.nIn(numHiddenNeurons);
    outputLayerBuilder.nOut(1);
    listBuilder.layer(numHiddenLayers, outputLayerBuilder.build());
    listBuilder.pretrain(false);
    listBuilder.backprop(true);

    MultiLayerConfiguration conf = listBuilder.build();
    net = new MultiLayerNetwork(conf);
    net.init();
    net.setListeners(new ScoreIterationListener(1));
  }

  private double normalizeChange(double input) {
    return (input + 1.0) * 0.5;
  }

  private double deNormalizeChange(double input) {
    return (input / 0.5) - 1.0;
  }

  private List<Double> normalizeChanges(List<Double> prices) {
    return prices
            .stream().map(this::normalizeChange).collect(Collectors.toList());
  }

  private double normalizeValue(double input) {
    return (input) / (2000) * 0.8 + 0.1;
  }

  private double deNormalizeValue(double input) {
    return (input - 0.1) * (2000) / 0.8;
  }

  private List<Double> normalizeValues(List<Double> prices) {
//    max = Collections.max(prices);
//    min = Collections.min(prices);
    return prices
            .stream().map(this::normalizeValue).collect(Collectors.toList());
  }

  private DataSet getTrainingData(List<Price> companyPriceSeries) {
    List<Double> normalizedPrices = normalizeValues(companyPriceSeries.stream().map(Price::getValue).collect(Collectors.toList()));
    INDArray inputs = Nd4j.zeros(1, numInputs, companyPriceSeries.size() - 1);
    INDArray labels = Nd4j.zeros(1, numInputs, companyPriceSeries.size() - 1);

    for (int i = 0; i < companyPriceSeries.size() - numInputs; i++) {
      double[] trainingValues = new double[numInputs];
      for (int j = 0; j < numInputs; j++) {
        trainingValues[j] = normalizedPrices.get(i + j);
      }
      double currentPrice = companyPriceSeries.get(i).getValue();
      double nextPrice = companyPriceSeries.get(i + 1).getValue();

      inputs.putScalar(new int[]{0, 0, i}, currentPrice);
      labels.putScalar(new int[]{0, 0, i}, nextPrice);
    }

    return new DataSet(inputs, labels);
  }

  @Override
  public void train(List<Price> companyPriceSeries, int epochs) {
    DataSet trainingData = getTrainingData(companyPriceSeries);
    System.out.println(trainingData);

    for (int epoch = 0; epoch < epochs; epoch++) {
      System.out.println("Epoch " + epoch);

      net.fit(trainingData);
      net.rnnClearPreviousState();
    }
  }

  @Override
  public Price run(List<Price> priceSeries, List<Integer> sentiments) {
    INDArray inputs = Nd4j.zeros(1, numInputs, priceSeries.size());

    for (int i = 0; i < priceSeries.size(); i++) {
      double currentPrice = priceSeries.get(i).getValue();

      inputs.putScalar(new int[]{0, 0, i}, currentPrice);
    }

    INDArray output = net.rnnTimeStep(inputs);
    System.out.println(output);

    return null;
  }

  public void saveModel(String s) throws IOException {
    File locationToSave = new File(s);
    ModelSerializer.writeModel(net, locationToSave, true);
  }

  public void readModel(String s) throws IOException {
    File locationToSave = new File(s);
    this.net = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
  }
}
