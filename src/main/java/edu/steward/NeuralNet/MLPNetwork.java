package edu.steward.NeuralNet;

import com.google.common.collect.ImmutableList;
import edu.steward.stock.Fundamentals.Price;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
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
  private int iterations = 10000;
  private double learningConstant = 0.5;
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
    max = 2000.0;
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

  private List<Double> normalizeValues(List<Double> prices) {
//    max = Collections.max(prices);
//    min = Collections.min(prices);
    return prices
            .stream().map(this::normalizeValue).collect(Collectors.toList());
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
  public void train(List<Price> priceSeries) {
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

    List<Double> prices = priceSeries
            .stream().map(Price::getValue).collect(Collectors.toList());
    DataSet trainingSet = loadTrainingData(prices);

    nn.learn(trainingSet);
  }

  @Override
  public Price run(List<Price> priceSeries, List<Integer> sentiments) {
    if (priceSeries.size() != nn.getInputsCount()) {
      return null;
    } else {
      double[] inputs = new double[movingWindow];
      for (int i = 0; i < movingWindow; i++) {
        inputs[i] = normalizeValue(priceSeries.get(i).getValue());
      }
      nn.setInput(inputs);
      nn.calculate();
      return new Price(deNormalizeValue(nn.getOutput()[0]),
              System.currentTimeMillis() / 1000 + 432000);
    }
  }

  public static void main(String[] args) throws IOException {
    DateTimeFormatter formatter1 =
            DateTimeFormat.forPattern("yyyy-MM-dd");
//    List<List<Price>> priceSeries = new ArrayList<>();
//
//    BufferedReader reader = new BufferedReader(new FileReader(
//            "C:\\Users\\Philip\\IdeaProjects\\Steward\\data\\aapl-1.csv"));
//
//    String line = reader.readLine();
//    String[] companies = line.split(",");
//
//    for (int i = 0; i < companies.length - 1; i++) {
//      priceSeries.add(new ArrayList<>());
//    }
//
//    while ((line = reader.readLine()) != null) {
//      String[] attributes = line.split(",");
//      for (int i = 1; i < attributes.length; i++) {
//        if (!attributes[i].equals("NaN")) {
//          priceSeries.get(i - 1).add(new Price(Double.parseDouble(attributes[i]),
//                  formatter1.parseDateTime(attributes[0]).getMillis() / 1000));
//        }
//      }
//    }
//
//    for (int i = 1; i < companies.length; i++) {
//      MLPNetwork mlp = new MLPNetwork(15);
//      mlp.train(priceSeries.get(i - 1));
//      mlp.saveModel("C:\\Users\\Philip\\IdeaProjects\\Steward\\data\\technology\\" + companies[i]);
//    }

    MLPNetwork mlp = new MLPNetwork(15);
    mlp.readModel("data/technology/AAPL");

    List<Price> testSeries =
            ImmutableList.of(new Price(143.699997, formatter1.parseDateTime("2017-04-03").getMillis() / 1000),
                    new Price(144.770004, formatter1.parseDateTime("2017-04-04").getMillis() / 1000),
                    new Price(144.020004, formatter1.parseDateTime("2017-04-05").getMillis() / 1000),
                    new Price(143.660004, formatter1.parseDateTime("2017-04-06").getMillis() / 1000),
                    new Price(143.339996, formatter1.parseDateTime("2017-04-07").getMillis() / 1000),
                    new Price(143.169998, formatter1.parseDateTime("2017-04-10").getMillis() / 1000),
                    new Price(141.630005, formatter1.parseDateTime("2017-04-11").getMillis() / 1000),
                    new Price(141.800003, formatter1.parseDateTime("2017-04-12").getMillis() / 1000),
                    new Price(141.050003, formatter1.parseDateTime("2017-04-13").getMillis() / 1000),
                    new Price(141.830002, formatter1.parseDateTime("2017-04-17").getMillis() / 1000),
                    new Price(141.199997, formatter1.parseDateTime("2017-04-18").getMillis() / 1000),
                    new Price(140.679993, formatter1.parseDateTime("2017-04-19").getMillis() / 1000),
                    new Price(142.440002, formatter1.parseDateTime("2017-04-20").getMillis() / 1000),
                    new Price(142.270004, formatter1.parseDateTime("2017-04-21").getMillis() / 1000),
                    new Price(143.639999, formatter1.parseDateTime("2017-04-24").getMillis() / 1000));


    System.out.println(mlp.run(testSeries, null));
  }
}
