package edu.steward.sql;

import edu.steward.ai.traders.Trader;
import edu.steward.user.Portfolio;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Philip on 5/5/17.
 */
public class ExecuteAiTransacton implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    List<Trader> aiPortfolios = aiRetriever.getActiveAIs();
    for (Trader p : aiPortfolios) {
      p.executeTransaction();
      System.out.println("something");
    }
  }
}
