package cn.gaohongtao.iw.task;

import cn.gaohongtao.iw.task.job.RefreshToken;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.client.ClientBuilder;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Task Factory
 * Created by gaoht on 15/6/26.
 */
public class TaskFactory {

    public static void main(String[] args) throws SchedulerException {
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(RefreshToken.class)
                .withIdentity("job1", "group1")
                .build();

// Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .build();

// Tell quartz to schedule the job using our trigger
        Scheduler sch = StdSchedulerFactory.getDefaultScheduler();
        sch.scheduleJob(job, trigger);
        sch.start();
    }

}
