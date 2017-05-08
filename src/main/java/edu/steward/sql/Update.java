package edu.steward.sql;

import com.google.common.collect.ImmutableMap;
import edu.steward.pools.Pool;
import edu.steward.user.Portfolio;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mrobins on 5/3/17.
 */
public class Update {

  private static String base = "jdbc:sqlite:";
  private static String userUrl = base + "data/users.sqlite3";

  private Update() {
  }

  public static void update() {

    String query = "SELECT PoolId, End FROM Pools;";
    try (Connection c = DriverManager.getConnection(userUrl)) {
      Statement s = c.createStatement();
      s.executeUpdate("PRAGMA foreign_keys = ON;");
      try (PreparedStatement prep = c.prepareStatement(query)) {
        try (ResultSet rs = prep.executeQuery()) {
          while (rs.next()) {
            String poolId = rs.getString(1);
            int end = rs.getInt(2);
            System.out.println("hmm: " + end);
            Pool.setEndTimer(poolId, end);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    JobDetail jobFiveMin = JobBuilder.newJob(FiveMinJob.class)
            .withIdentity("jobFive", "groupFive").build();

    Set<Trigger> triggerSetFive = new HashSet<>();

    // Trigger every 5 minutes between 10:00am-4:00pm
    Trigger triggerFive1 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerFive1", "groupFive")
            .withSchedule(cronSchedule("0 0/5 10-15 ? * MON-FRI"))
            .forJob(jobFiveMin)
            .build();

    triggerSetFive.add(triggerFive1);

    // Trigger every 5 minutes between 9:30am-10:00am
    Trigger triggerFive2 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerFive2", "groupFive")
            .withSchedule(cronSchedule("0 30/5 9 ? * MON-FRI"))
            .forJob(jobFiveMin)
            .build();

    triggerSetFive.add(triggerFive2);

//    Trigger at 4:00 p.m. market close
    Trigger triggerFive3 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerFive3", "groupFive")
            .withSchedule(cronSchedule("0 0 16 ? * MON-FRI"))
            .forJob(jobFiveMin)
            .build();

    triggerSetFive.add(triggerFive3);

    // schedule it
    try {
      Scheduler schedulerFive = new StdSchedulerFactory().getScheduler();
      schedulerFive.start();
      schedulerFive.scheduleJob(jobFiveMin, triggerSetFive, true);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

    JobDetail jobThirtyMin = JobBuilder.newJob(ThirtyMinJob.class)
            .withIdentity("jobThirty", "groupThirty").build();

    Set<Trigger> triggerSetThirty = new HashSet<>();

    // Trigger every 30 minutes between 10:00am-4:00pm
    Trigger triggerThirty1 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerThirty1", "groupThirty")
            .withSchedule(cronSchedule("0 0/30 10-15 ? * MON-FRI"))
            .forJob(jobThirtyMin)
            .build();

    triggerSetThirty.add(triggerThirty1);

    // Trigger every 5 minutes between 9:30am-10:00am
    Trigger triggerThirty2 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerThirty2", "groupThirty")
            .withSchedule(cronSchedule("0 30/30 9 ? * MON-FRI"))
            .forJob(jobThirtyMin)
            .build();

    triggerSetThirty.add(triggerThirty2);

    //    Trigger at 4:00 p.m. market close
    Trigger triggerThirty3 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerThirty3", "groupThirty")
            .withSchedule(cronSchedule("0 0 16 ? * MON-FRI"))
            .forJob(jobThirtyMin)
            .build();

    triggerSetThirty.add(triggerThirty3);

    // schedule it
    try {
      Scheduler schedulerThirty = new StdSchedulerFactory().getScheduler();
      schedulerThirty.start();
      schedulerThirty.scheduleJob(jobThirtyMin, triggerSetThirty, true);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

    JobDetail jobDaily = JobBuilder.newJob(DailyJob.class)
            .withIdentity("jobDaily", "groupDaily").build();

    // Trigger every day at 4:00pm
    Trigger triggerDaily = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerDaily", "groupDaily")
            .withSchedule(cronSchedule("0 0 16 ? * MON-FRI"))
            .forJob(jobDaily)
            .build();

    // schedule it
    try {
      Scheduler schedulerDaily = new StdSchedulerFactory().getScheduler();
      schedulerDaily.start();
      schedulerDaily.scheduleJob(jobDaily, triggerDaily);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

//    JobDetail watchlistUpdate = JobBuilder.newJob(WatchListJob.class)
//            .withIdentity("watchlistUpdate", "groupWatchlist").build();
//
////  Trigger every hour
//    Trigger triggerWatchlist = TriggerBuilder
//            .newTrigger()
//            .withIdentity("triggerWatchlist", "groupWatchlist")
////            TODO: Uncomment this, this is what we want
////            .withSchedule(cronSchedule("0 0 0-23 ? * ?"))
//            .withSchedule(cronSchedule("0 0/1 * * * ?"))
//            .forJob(watchlistUpdate)
//            .build();
//    try {
//      Scheduler schedulerWatchlist = new StdSchedulerFactory().getScheduler();
//      schedulerWatchlist.start();
//      schedulerWatchlist.scheduleJob(watchlistUpdate, triggerWatchlist);
//    } catch (SchedulerException e) {
//      e.printStackTrace();
//    }

    JobDetail jobAI = JobBuilder.newJob(ExecuteAiTransacton.class)
            .withIdentity("jobAI", "groupAI").build();

    // Trigger every hour between 10:00am-4:00pm
    Trigger triggerAI = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerAI1", "groupAI")
            .withSchedule(cronSchedule("0 0 0/3 * * ?"))
            .forJob(jobAI)
            .build();

    // schedule it
    try {
      Scheduler schedulerAI = new StdSchedulerFactory().getScheduler();
      schedulerAI.start();
      schedulerAI.scheduleJob(jobAI, triggerAI);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
