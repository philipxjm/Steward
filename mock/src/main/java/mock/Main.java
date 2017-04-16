package mock;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Main mock class.
 *
 * @author wpovell
 *
 */
public final class Main {
  public static final int PORT = 8080;
  private String[] args;
  private FreeMarkerEngine freeMarker;
  private static final Gson GSON = new Gson();

  /**
   * Entry point. Command line args are passed into main constructor and then
   * run.
   *
   * @param args
   *          Command line args.
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private Main(String[] args) {
    this.args = args;
    freeMarker = createEngine();
  }

  private void run() {
    Spark.port(PORT);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    Spark.post("/getStockData", new GetStockData());
  }

  /**
   * API endpoint for getStockData.
   *
   * @author wpovell
   *
   */
  private class GetStockData implements Route {
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String ticker = qm.value("ticker");
      int start = Integer.parseInt(qm.value("start"));
      int end = Integer.parseInt(qm.value("end"));

      List<List<Double>> ret = new ArrayList<>();
      double p = 50;
      int inter = (end - start) / 100;
      for (double i = start; i <= end; i += inter) {
        double newP = p + Math.random() * 10 - 5;
        ret.add(ImmutableList.of(i, newP));
        p = newP;
      }
      return GSON.toJson(ret);
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   * @author wpovell
   */
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

  /**
   * Returns freemarker for use with spark templating.
   *
   * @return FreeMarkerEngine
   */
  private static FreeMarkerEngine createEngine() {
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
}
