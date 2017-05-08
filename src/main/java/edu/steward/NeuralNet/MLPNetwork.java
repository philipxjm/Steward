package edu.steward.NeuralNet;

import com.google.common.collect.ImmutableList;
import edu.steward.Sentiment.SentimentWrapper;
import edu.steward.stock.Fundamentals.Price;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.ElmanNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Philip on 4/22/2017.
 */
public class MLPNetwork implements NeuralNet {

  private double min, max;
  private int movingWindow;
  private int iterations = 25000;
  private double learningConstant = 1.5;
  private double maxError = 0.00000001;
  private NeuralNetwork nn;

  public MLPNetwork(int movingWindow) {
    min = 0.0;
    max = 2000.0;
    this.movingWindow = movingWindow;
    nn = new MultiLayerPerceptron(movingWindow, 2 * movingWindow + 1, 1);
  }

  public MLPNetwork(int movingWindow, String pathToModel) {
    min = 0.0;
    max = 5000.0;
    this.movingWindow = movingWindow;
    nn = NeuralNetwork.createFromFile(pathToModel);
  }

  public void readModel(String pathToModel) {
    nn = NeuralNetwork.createFromFile(pathToModel);
  }

  public void saveModel(String pathToModel) {
    nn.save(pathToModel);
  }

  public void setIterations(int iterations) {
    this.iterations = iterations;
  }

  public void setLearningConstant(double learningConstant) {
    this.learningConstant = learningConstant;
  }

  public void setMaxError(double maxError) {
    this.maxError = maxError;
  }

  private double normalizeValue(double input) {
    return (input - min) / (max - min) * 0.8 + 0.1;
  }

  private double deNormalizeValue(double input) {
    return min + (input - 0.1) * (max - min) / 0.8;
  }

  private double normalizeChange(double input) {
    return (input + 1.0) * 0.5;
  }

  private double deNormalizeChange(double input) {
    return (input / 0.5) - 1.0;
  }

  private List<Double> normalizeValues(List<Double> prices) {
//    max = Collections.max(prices);
//    min = Collections.min(prices);
    return prices
            .stream().map(this::normalizeValue).collect(Collectors.toList());
  }

  private List<Double> normalizeChanges(List<Double> prices) {
//    max = Collections.max(prices);
//    min = Collections.min(prices);
    return prices
            .stream().map(this::normalizeChange).collect(Collectors.toList());
  }

  private DataSet loadTrainingData(List<Double> prices) {
    List<Double> normalizedPrices = normalizeValues(prices);
    DataSet set = new DataSet(movingWindow, 1);
    for (int i = 0; i < normalizedPrices.size() - movingWindow; i++) {
      double[] trainingValues = new double[movingWindow];
      for (int j = 0; j < movingWindow; j++) {
        trainingValues[j] = normalizedPrices.get(i + j);
      }
      set.addRow(new DataSetRow(trainingValues, new double[]{normalizedPrices.get(i + movingWindow)}));
    }
    return set;
  }

  @Override
  public void train(List<Price> priceSeries, int epochs) {
    SupervisedLearning learningRule = (SupervisedLearning) nn.getLearningRule();
    learningRule.setMaxError(maxError);
    learningRule.setLearningRate(learningConstant);
    learningRule.setMaxIterations(iterations);
    learningRule.addListener(learningEvent -> {
      SupervisedLearning rule = (SupervisedLearning) learningEvent.getSource();
      System.out.println("Network error for interation "
              + rule.getCurrentIteration()
              + ": "
              + rule.getTotalNetworkError());
    });

    List<Double> prices = priceSeries.stream().map(Price::getValue).collect(Collectors.toList());
//    List<Double> changes = Trainer.priceSeriesToChangeSeries(priceSeries);
//    System.out.println(changes);
//    System.exit(1);
    DataSet trainingSet = loadTrainingData(prices);

    nn.learn(trainingSet);
  }

  @Override
  public Price run(List<Price> priceSeries, List<Integer> sentiments) {
    double totalChanges = 0.0;
    for (int i = 0; i < priceSeries.size() - 1; i++) {
      totalChanges += (priceSeries.get(i + 1).getValue() - priceSeries.get(i)
              .getValue()) / priceSeries.get(i).getValue();
    }

    SentimentWrapper sw = new SentimentWrapper();
    double averageChange = totalChanges / priceSeries.size();
    double sentiment = sw.findSentimentOf("AAPL");

    System.out.println(averageChange);

    if (sentiment > 0.5) {
      averageChange = Math.abs(averageChange) * sentiment * 3.0;
    } else {
      averageChange = -Math.abs(averageChange) * sentiment * 3.0;
    }

    if (priceSeries.size() != nn.getInputsCount()) {
      return null;
    } else {
      double[] inputs = new double[movingWindow];
      for (int i = 0; i < movingWindow; i++) {
        inputs[i] = normalizeValue(priceSeries.get(i).getValue());
      }
      nn.setInput(inputs);
      nn.calculate();

      double nnOutput = deNormalizeValue(nn.getOutput()[0]);
      double difference = (nnOutput - priceSeries.get(priceSeries.size() - 1)
              .getValue());

      if (Math.abs(difference) > 0.5) {
        difference = averageChange + 0.5;
      } else {
        difference = averageChange + difference;
      }

      return new Price(priceSeries.get(priceSeries.size() - 1)
              .getValue() + difference,
              System.currentTimeMillis() / 1000 + 432000);
    }
  }
}
