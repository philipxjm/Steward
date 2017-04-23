package edu.steward.main;


import edu.steward.handlers.*;
import edu.steward.login.LoginConfigFactory;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Stock;
import edu.steward.user.UserSession;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.CallbackRoute;
import org.pac4j.sparkjava.SecurityFilter;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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

//    AlphaVantageAPI api = new AlphaVantageAPI();
//    List<Price> prices = api.getStockPrices("AAPL", StockAPI.TIMESERIES.ONE_YEAR);
//    int counter = 0;
//    for (Price p:
//         prices) {
//      counter++;
//      System.out.println("time: " + p.getTime() + ", price: " + p.getValue());
//    }
//    System.out.println(counter);

//    new YahooFinanceAPI().func();

    List<Fundamental> fundamentals = new Stock("AAPL").getStockFundamentals();
    for (Fundamental fundamental : fundamentals) {
      System.out.println(fundamental.toString() + ": " + fundamental.getValue());
    }

    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);


    if (options.has("gui")) {
      runSparkServer((Integer) options.valueOf("port"));
    }
  }

  public static FreeMarkerEngine createEngine() {
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
    final Config config = new LoginConfigFactory().build();
    FreeMarkerEngine freeMarker = createEngine();
    final CallbackRoute callback = new CallbackRoute(config, null, true);

    // Spark routes
    Spark.get("/", new IndexHandler(), freeMarker);
    Spark.get("/about", new AboutHandler(), freeMarker);
    Spark.post("/getGraphData", new GetGraphDataMock());
    Spark.get("/stock/:ticker", new StockMock(), freeMarker);
//    Spark.post("/getStockData", new GetStockDataMock());
    Spark.get("/callback", callback);
    Spark.post("/callback", callback);
    Spark.before("/google", new SecurityFilter(config,
        "OidcClient"));
    Spark.get("/google", UserSession::destPage, freeMarker);
    Spark.post("/dashboard", new DashboardMock(), freeMarker);
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
