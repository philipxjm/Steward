package edu.steward.pools;

import java.util.*;

import edu.steward.sql.DatabaseApi;
import edu.steward.sql.InsertFinalLb;
import edu.steward.sql.Update;
import edu.steward.user.Portfolio;

/**
 * Created by kjin on 5/1/17.
 */
public class Pool {

  private List<Portfolio> portfolios;
  private int bal;
  private long start;
  private String id;
  private String name;
  private long end;

  public Pool(String name, int balance, long startTime, long end,
      Portfolio... ports) {
    bal = balance;
    start = startTime;
    this.name = name;
    this.end = end + 72000;
    generateRandom();
    id = id.toUpperCase();
    while (DatabaseApi.getPool(id) != null) {
      generateRandom();
    }
    portfolios = Arrays.asList(ports);
    DatabaseApi.initializePool(this);
//    TODO: uncomment this
    setEndTimer();
  }

  private void generateRandom() {
    Random r = new Random();
    id = "";
    while (id.length() < 4) {
      id = id + Integer.toString(r.nextInt(36), 36);
    }

  }

  public Pool(String id, String name, int balance, long startTime, long endTime,
      Portfolio... ports) {
    this.id = id;
    this.bal = balance;
    this.start = startTime;
    this.end = endTime;
    this.name = name;
    portfolios = Arrays.asList(ports);
  }

  public void addPortfolio(Portfolio port) {
    portfolios.add(port);
  }

  public List<Portfolio> getPortfolios() {
    return portfolios;
  }

  public void setPortfolios(List<Portfolio> portfolios) {
    this.portfolios = portfolios;
  }

  public int getBal() {
    return bal;
  }

  public void setBal(int bal) {
    this.bal = bal;
  }

  public long getStart() {
    return start;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(long end) {
    this.end = end;
  }

  public void setEndTimer() {

    long delay = 1000L * end - System.currentTimeMillis();

    Timer t = new Timer();
    t.schedule(new TimerTask() {

      @Override
      public void run() {
        InsertFinalLb.insert(id);
      }
    }, delay);
  }

  public static void setEndTimer(String poolId, int endTime) {
//    TODO: uncomment this
    long delay = 1000L * endTime - System.currentTimeMillis();

    Timer t = new Timer();
    t.schedule(new TimerTask() {

      @Override
      public void run() {
        InsertFinalLb.insert(poolId);
      }
    },delay / 1000L);
  }
}
