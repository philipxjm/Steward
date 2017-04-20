package edu.steward.analytics;

import com.google.common.collect.ImmutableList;
import edu.steward.NeuralNet.NaiveNeuralNetwork;
import edu.steward.NeuralNet.NeuralNet;
import edu.steward.Sentiment.TwitterSentimentFinder;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.Stock;
import edu.steward.stock.api.AlphaVantageAPI;
import edu.steward.stock.api.StockAPI;

import java.util.List;

/**
 * Created by Philip on 4/19/2017.
 */
public class NaiveStockPredictor implements StockPredictor{
    private NeuralNet shortRangeNN;
    private NeuralNet mediumRangeNN;
    private TwitterSentimentFinder tsf;

    public NaiveStockPredictor(NeuralNet mediumRangeNN,
                               NeuralNet shortRangeNN,
                               TwitterSentimentFinder tsf) {
        this.shortRangeNN = shortRangeNN;
        this.mediumRangeNN = mediumRangeNN;
        this.tsf = tsf;
    }

    @Override
    public Price getFiveDayPrediction(String ticker) {
        Stock stock = new Stock(ticker);
        Stock.setStockAPI(new AlphaVantageAPI());
        List<Price> series = stock.getStockPrices(StockAPI.TIMESERIES.ONE_MONTH);
        double pricePrediction
                = mediumRangeNN.run(series, tsf.sentiments(ImmutableList.of(ticker)).get(ticker));
        return new Price(pricePrediction, System.currentTimeMillis() / 1000 + 432000);
    }

    @Override
    public List<Price> getPredictions(String ticker) {
        return null;
    }

    public static void main(String[] args) {
        StockPredictor sp = new NaiveStockPredictor(new NaiveNeuralNetwork(),
                new NaiveNeuralNetwork(),
                new TwitterSentimentFinder());
        System.out.println(sp.getFiveDayPrediction("NVDA"));
    }
}
