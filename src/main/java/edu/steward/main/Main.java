package edu.steward.main;


import edu.steward.mock.GetGraphDataMock;
import edu.steward.mock.StockMock;
import edu.steward.stock.Fundamentals.Price;
import edu.steward.stock.api.AlphaVantageAPI;
import edu.steward.stock.api.AlphaVantageConstants;
import com.google.common.collect.ImmutableList;
import edu.steward.login.LoginConfigFactory;
import edu.steward.user.UserSession;
import edu.steward.stock.api.StockAPI;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.CallbackRoute;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.pac4j.sparkjava.SecurityFilter;
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
    List<Price> prices = api.getStockPrices("MSFT", StockAPI.TIMESERIES.ONE_DAY);
    for (Price p:
         prices) {
      System.out.println("time: " + p.getTime() + ", price: " + p.getValue());
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
    final Config config = new LoginConfigFactory().build();
    FreeMarkerEngine freeMarker = createEngine();
    final CallbackRoute callback = new CallbackRoute(config, "/", true);

    // Todo: Set up Spark handlers
    Spark.post("/getStockData", new GetGraphDataMock());
    Spark.get("/stock/:ticker", new StockMock(), freeMarker);
//    Spark.post("/getStockData", new GetStockDataMock());
    Spark.get("/callback", callback);
    Spark.post("/callback", callback);
    Spark.before("/google", new SecurityFilter(config,
        "Google2Client"));
    Spark.get("/google", UserSession::destPage, freeMarker);
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
