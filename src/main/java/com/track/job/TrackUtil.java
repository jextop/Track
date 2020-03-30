package com.track.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TrackUtil {
    private static final String CRON = "0/7 * * * * ?";
    private static final JobDetail JOB;

    static {
        // 创建任务，模拟多个客户端
        JOB = JobBuilder.newJob(TrackJob.class)
                .build();
    }

    public static void stop() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.deleteJob(JOB.getKey());
        } catch (SchedulerException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void start() {
        // 创建定时器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(CRON);

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 配置任务
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(JOB)
                    .withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(JOB, trigger);

            // 开启任务
            scheduler.startDelayed(1);
        } catch (SchedulerException e) {
            System.err.println(e.getMessage());
        }

        // 立即发送一次
        try {
            new TrackJob().execute(null);
        } catch (JobExecutionException e) {
            System.err.println(e.getMessage());
        }
    }
}
