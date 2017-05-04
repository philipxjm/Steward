package edu.steward.handlers.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.steward.data.CSVReader;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class SuggestHandler implements Route {
  static final Gson gson = new Gson();
  private Trie<String, StockSuggest> suggest;

  private class StockSuggest implements Comparable<StockSuggest> {
    String ticker, name;
    double cap;

    public StockSuggest(String ticker, String name, double cap) {
      this.ticker = ticker;
      this.name = name;
      this.cap = cap;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof StockSuggest)) {
        return false;
      }
      StockSuggest s = (StockSuggest) o;
      return name == s.name && ticker == s.ticker && cap == s.cap;
    }

    @Override
    public int hashCode() {
      return Objects.hash(ticker, name, cap);
    }

    @Override
    public int compareTo(StockSuggest o) {
      return Double.compare(o.cap, cap);
    }
  }

  public SuggestHandler(List<String> fns) {
    try {
      Map<String, StockSuggest> toMake = new HashMap<>();
      for (String fn : fns) {
        CSVReader csv = new CSVReader(fn);
        for (String[] line : csv.getLines()) {
          String sym = line[0].replace("\"", "");
          if (!sym.chars().allMatch(Character::isLetter)) {
            continue;
          }
          String name = line[1].replace("\"", "");
          try {
            double cap = Double.parseDouble(line[3].replace("\"", ""));

            StockSuggest inner = new StockSuggest(sym, name, cap);
            if (!toMake.containsKey(sym.toLowerCase())) {
              toMake.put(sym.toLowerCase(), inner);
              toMake.put(name.toLowerCase(), inner);
            }
          } catch (NumberFormatException e) {
          }
        }
      }
      suggest = new PatriciaTrie<StockSuggest>(toMake);
    } catch (NoSuchElementException | IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String handle(Request req, Response res) throws Exception {
    QueryParamsMap qm = req.queryMap();
    String input = qm.value("input");
    Set<StockSuggest> suggests = new HashSet<>();
    suggests.addAll(suggest.prefixMap(input).values());
    List<StockSuggest> toSort = new ArrayList<>();
    toSort.addAll(suggests);
    Collections.sort(toSort);
    toSort = toSort.subList(0, Math.min(suggests.size(), 10));

    Set<List<String>> ret = toSort.stream()
        .map((StockSuggest s) -> ImmutableList.of(s.ticker, s.name))
        .collect(Collectors.toSet());

    return gson.toJson(ret);
  }

}
