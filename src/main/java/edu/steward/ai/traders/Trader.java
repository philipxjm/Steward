package edu.steward.ai.traders;

import edu.steward.Sentiment.Watchlist;
import edu.steward.stock.Stock;
import edu.steward.user.Portfolio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Philip on 5/5/17.
 */
public class Trader extends Portfolio {
  public Trader(String name, String portfolioId) {
    super(name, portfolioId);
    executeEntry();
  }

  public static List<String> goodShit() {
    Map<String, Double> sentiments
            = sortByValue(Watchlist.trendingSentiments());
    List<String> list = new ArrayList<String>();
    for (Map.Entry<String, Double> entry : sentiments.entrySet()) {
      if (list.size() > 4) break;
      list.add(entry.getKey());
    }
    return list;
  }

  private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue
          (Map<K, V> map) {
    return map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
  }

  public void executeTransaction() {
    executeExit();
    executeEntry();
  }

  private boolean executeEntry() {
    System.out.println("bought shit");
    int currTime = (int) (System.currentTimeMillis() / 1000L);
    List<Stock> goodStocks = goodShit()
            .stream()
            .map(Stock::new)
            .collect(Collectors.toList());
    double singleStockCash = getBalance() / 5.0;
    for (Stock shit : goodStocks) {
      System.out.println("buying " + shit.getTicker() + " -- " + shit.getCurrPrice());
      buyStock(shit.getTicker(),
              (int) Math.floor(singleStockCash / shit.getCurrPrice().getValue()),
              currTime,
              shit.getCurrPrice().getValue());
    }
    return true;
  }

  private boolean executeExit() {
    System.out.println("sold shit");
    int currTime = (int) (System.currentTimeMillis() / 1000L);
    List<String> ownedStocks = new ArrayList<String>(getHoldings().keySet());
    for (String ticker : ownedStocks) {
      sellStock(ticker,
              getHoldings().get(ticker),
              currTime,
              (new Stock(ticker)).getCurrPrice().getValue());
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println(goodShit());
  }
}
