package edu.steward.sql;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static edu.steward.sql.DailyDataUpdater.updateThirty;

/**
 * Created by mrobins on 5/3/17.
 */
public class ThirtyMinJob implements Job{
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    updateThirty();
  }
}
