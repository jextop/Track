package com.track.job;

import com.track.util.MacUtil;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

public class TrackUtil {
    private static final String cron = "0/1 * * * * ?";
    private static final int COUNT = 30;

    private static final List<JobDetail> jobList;

    static {
        jobList = new ArrayList<JobDetail>();

        // 创建任务，模拟多个客户端
        for (int i = 0; i < COUNT; i++) {
            jobList.add(JobBuilder.newJob(TrackJob.class)
                    .withIdentity(String.format("%s%02d", MacUtil.gtMacAddr(), i + 1))
                    .build()
            );
        }
    }

    public static void stop() {
        for (JobDetail job : jobList) {
            try {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.deleteJob(job.getKey());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

    public static void start() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 定时
            for (JobDetail job : jobList) {
                Trigger trigger = TriggerBuilder.newTrigger()
                        .forJob(job)
                        .withSchedule(scheduleBuilder)
                        .build();

                scheduler.scheduleJob(job, trigger);
            }

            // 开启任务
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
