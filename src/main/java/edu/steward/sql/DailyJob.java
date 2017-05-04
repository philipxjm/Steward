package edu.steward.sql;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static edu.steward.sql.DailyDataUpdater.updateDaily;

/**
 * Created by mrobins on 5/3/17.
 */
public class DailyJob implements Job {


  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    updateDaily();
  }
}

