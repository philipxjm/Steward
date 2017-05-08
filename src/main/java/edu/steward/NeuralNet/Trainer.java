package edu.steward.NeuralNet;

import com.google.common.collect.ImmutableList;
import edu.steward.stock.Fundamentals.Price;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Philip on 5/7/2017.
 */
public class Trainer {
  public static List<Double> priceSeriesToChangeSeries(List<Price> priceSeries) {
    List<Double> changeSeries = new ArrayList<>();
    for (int i = 0; i < priceSeries.size() - 1; i++) {
      changeSeries.add((priceSeries.get(i + 1).getValue() - priceSeries.get(i).getValue()) / priceSeries.get(i).getValue());
    }
    return changeSeries;
  }

  public static void trainNetwork() throws IOException {
    DateTimeFormatter formatter1 =
            DateTimeFormat.forPattern("yyyy-MM-dd");
    List<List<Price>> priceSeries = new ArrayList<>();

    BufferedReader reader = new BufferedReader(new FileReader(
            "data/aapl.csv"));

    String line = reader.readLine();
    String[] companies = line.split(",");

    for (int i = 0; i < companies.length - 1; i++) {
      priceSeries.add(new ArrayList<>());
    }

    while ((line = reader.readLine()) != null) {
      String[] attributes = line.split(",");
      for (int i = 1; i < attributes.length; i++) {
        if (!attributes[i].equals("NaN")) {
          priceSeries.get(i - 1).add(new Price(Double.parseDouble(attributes[i]),
                  formatter1.parseDateTime(attributes[0]).getMillis() / 1000));
        }
      }
    }

//    MLPNetwork mlp = new MLPNetwork(15);
    Rnn rnn = new Rnn(1, 2);

    for (int i = 1; i < companies.length; i++) {
      rnn.train(priceSeries.get(i - 1), 1);
      rnn.saveModel("data/technology/GoodShit.zip");
    }
  }

  public static void testNetwork() throws IOException {
    DateTimeFormatter formatter1 =
            DateTimeFormat.forPattern("yyyy-MM-dd");

//    MLPNetwork mlp = new MLPNetwork(15);
//    mlp.readModel("data/technology/GoodShit");
    Rnn rnn = new Rnn(1, 2);
    rnn.readModel("data/technology/GoodShit.zip");

    List<Price> testSeries =
            ImmutableList.of(new Price(141.83, formatter1.parseDateTime("2017-04-03").getMillis() / 1000),
                    new Price(149.20, formatter1.parseDateTime("2017-04-04").getMillis() / 1000),
                    new Price(149.68, formatter1.parseDateTime("2017-04-05").getMillis() / 1000),
                    new Price(148.44, formatter1.parseDateTime("2017-04-06").getMillis() / 1000),
                    new Price(144.27, formatter1.parseDateTime("2017-04-07").getMillis() / 1000),
                    new Price(145.64, formatter1.parseDateTime("2017-04-10").getMillis() / 1000),
                    new Price(146.53, formatter1.parseDateTime("2017-04-11").getMillis() / 1000),
                    new Price(149.68, formatter1.parseDateTime("2017-04-12").getMillis() / 1000),
                    new Price(149.79, formatter1.parseDateTime("2017-04-13").getMillis() / 1000),
                    new Price(149.65, formatter1.parseDateTime("2017-04-17").getMillis() / 1000),
                    new Price(150.58, formatter1.parseDateTime("2017-04-18").getMillis() / 1000),
                    new Price(150.51, formatter1.parseDateTime("2017-04-19").getMillis() / 1000),
                    new Price(150.06, formatter1.parseDateTime("2017-04-20").getMillis() / 1000),
                    new Price(150.53, formatter1.parseDateTime("2017-04-21").getMillis() / 1000),
                    new Price(150.96, formatter1.parseDateTime("2017-04-24").getMillis() / 1000));


    System.out.println(rnn.run(testSeries, null));
  }

  public static void main(String[] args) throws IOException {
    testNetwork();
  }
}
