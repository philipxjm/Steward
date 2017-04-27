package edu.steward.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.steward.stock.Stock;
import edu.steward.stock.api.YahooFinanceAPI;
import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.CallbackRoute;

import edu.steward.handlers.html.AboutHandler;
import edu.steward.handlers.html.IndexHandler;
import edu.steward.handlers.html.StockHandler;
import edu.steward.handlers.json.GetGraphDataHandler;
import edu.steward.handlers.json.GetPortfolioHandler;
import edu.steward.handlers.json.GetUnrealizedDataHandler;
import edu.steward.handlers.json.LoginHandler;
import edu.steward.handlers.json.LogoutHandler;
import edu.steward.handlers.json.NewPortfolioHandler;
import edu.steward.handlers.json.StockActionHandler;
import edu.steward.login.LoginConfigFactory;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    // Workaround for redirect issue (#69) in yahoo finance library
//     System.setProperty("yahoofinance.baseurl.histquotes", "https://ichart.yahoo.com/table.csv");
//     System.setProperty("yahoofinance.baseurl.quotes", "http://download.finance.yahoo.com/d/quotes.csv");
//    Calendar from = Calendar.getInstance();
//    from.add(Calendar.YEAR, -10);
//    try {
//      yahoofinance.Stock fb = YahooFinance.get("FB");
//      fb.getQuote();
//      yahoofinance.Stock apple = YahooFinance.get("FB", true);
//      List<HistoricalQuote> quotes = apple.getHistory(from, Interval.WEEKLY);
//      for (HistoricalQuote quote : quotes) {
////        System.out.println(quote.getDate().getTimeInMillis() + ": " + quote.getAdjClose());
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }



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
    File templates = new File("src/main/resources/spark/template/freemarker");
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
    Spark.post("/newPortfolio", new NewPortfolioHandler());
    Spark.post("/getPortfolio", new GetPortfolioHandler());
    Spark.post("/stockAction", new StockActionHandler());
    Spark.get("/about", new AboutHandler(), freeMarker);
    Spark.post("/getGraphData", new GetGraphDataHandler());
    Spark.post("/getUnrealizedData", new GetUnrealizedDataHandler());
    Spark.get("/stock/:ticker", new StockHandler(), freeMarker);
    Spark.get("/login", new LoginHandler());
    Spark.get("/logout", new LogoutHandler());
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
