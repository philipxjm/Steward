package edu.steward.sql;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static edu.steward.Sentiment.Watchlist.trendingSentiments;
import static edu.steward.sql.DailyDataUpdater.updateThirty;

/**
 * Created by mrobins on 5/5/17.
 */
public class WatchListJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    trendingSentiments();
  }
}
