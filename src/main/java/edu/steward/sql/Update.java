package edu.steward.sql;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mrobins on 5/3/17.
 */
public class Update {

  public static void update() {

    JobDetail jobFiveMin = JobBuilder.newJob(FiveMinJob.class)
            .withIdentity("jobFive", "groupFive").build();

    Set<Trigger> triggerSetFive = new HashSet<>();

    // Trigger every 5 minutes between 10:00am-4:00pm
    Trigger triggerFive1 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerFive1", "groupFive")
            .withSchedule(cronSchedule("* 0/5 10-16 ? * MON-FRI"))
            .forJob(jobFiveMin)
            .build();

    triggerSetFive.add(triggerFive1);

    // Trigger every 5 minutes between 9:30am-10:00am
    Trigger triggerFive2 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerFive2", "groupFive")
            .withSchedule(cronSchedule("* 30/5 9-10 ? * MON-FRI"))
            .forJob(jobFiveMin)
            .build();

    triggerSetFive.add(triggerFive2);


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
            .withIdentity("triggerThirty", "groupThirty")
            .withSchedule(cronSchedule("* 0/30 10-16 ? * MON-FRI"))
            .forJob(jobThirtyMin)
            .build();

    triggerSetThirty.add(triggerThirty1);

    // Trigger every 5 minutes between 9:30am-10:00am
    Trigger triggerThirty2 = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerThirty", "groupThirty")
            .withSchedule(cronSchedule("* 30/30 9-10 ? * MON-FRI"))
            .forJob(jobThirtyMin)
            .build();

    triggerSetThirty.add(triggerThirty2);

    // schedule it
    try {
      Scheduler schedulerThirty = new StdSchedulerFactory().getScheduler();
      schedulerThirty.start();
      schedulerThirty.scheduleJob(jobThirtyMin, triggerSetThirty, true);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

    JobDetail jobDaily = JobBuilder.newJob(DailyJob.class)
            .withIdentity("jobThirty", "groupDaily").build();

    // Trigger every day at 4:00pm
    Trigger triggerDaily = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerThirty", "groupDaily")
            .withSchedule(cronSchedule("0 0 16 ? * MON-FRI"))
            .forJob(jobThirtyMin)
            .build();

    // schedule it
    try {
      Scheduler schedulerDaily = new StdSchedulerFactory().getScheduler();
      schedulerDaily.start();
      schedulerDaily.scheduleJob(jobThirtyMin, triggerDaily);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
