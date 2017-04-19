package edu.steward.main;



import edu.steward.mock.GetGraphDataMock;
import edu.steward.mock.StockMock;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.AlphaVantageAPI;
import edu.steward.stock.api.AlphaVantageConstants;
import edu.steward.stock.api.StockAPI;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.*;
import java.util.List;
import java.util.Map;


public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
//    System.out.println(TwitterSentiments.sentiments(ImmutableList.<String>of
//            ("Trump", "Syria")));
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments

    AlphaVantageAPI api = new AlphaVantageAPI();
    List<Price> prices = api.getStockPrices("MSFT", StockAPI.TIMESERIES.ONE_YEAR);
    int counter = 0;
    for (Price p:
         prices) {
      counter++;
      System.out.println("time: " + p.getTime() + ", price: " + p.getValue());
    }
    System.out.println(counter);

    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);


    if (options.has("gui")) {
      runSparkServer((Integer) options.valueOf("port"));
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates =
            new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
              templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Todo: Set up Spark handlers
    Spark.post("/getStockData", new GetGraphDataMock());
    Spark.get("/stock/:ticker", new StockMock(), freeMarker);
  }

  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}
