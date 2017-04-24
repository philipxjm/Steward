package edu.steward.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.CallbackRoute;

import edu.steward.handlers.AboutHandler;
import edu.steward.handlers.AddPortfolioHandler;
import edu.steward.handlers.GetGraphDataHandler;
import edu.steward.handlers.IndexHandler;
import edu.steward.handlers.LoginHandler;
import edu.steward.handlers.LogoutHandler;
import edu.steward.handlers.StockHandler;
import edu.steward.login.LoginConfigFactory;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
    // System.out.println(TwitterSentiments.sentiments(ImmutableList.<String>of
    // ("Trump", "Syria")));
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments

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
    Spark.post("/newPortfolio", new AddPortfolioHandler());
    Spark.get("/about", new AboutHandler(), freeMarker);
    Spark.post("/getGraphData", new GetGraphDataHandler());
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
