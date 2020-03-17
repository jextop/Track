package com.track.job;

import com.track.util.MacUtil;
import com.track.util.PoissonUtil;
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
    private static final String CRON = "0/7 * * * * ?";
    private static final int COUNT = 30;
    private static final List<JobDetail> JOB_LIST;

    static {
        JOB_LIST = new ArrayList<JobDetail>();

        // 创建任务，模拟多个客户端
        for (int i = 0; i < COUNT; i++) {
            JOB_LIST.add(JobBuilder.newJob(TrackJob.class)
                    .withIdentity(String.format("%s%02d", MacUtil.gtMacAddr(), i + 1))
                    .build()
            );
        }
    }

    public static void stop() {
        for (JobDetail job : JOB_LIST) {
            try {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.deleteJob(job.getKey());
            } catch (SchedulerException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void start() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(CRON);

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            for (JobDetail job : JOB_LIST) {
                // 泊松分布启动客户端
                int i = PoissonUtil.getPoissonVariable(3.25) * 50;
                if (i > 0) {
                    Thread.sleep(i);
                }
                System.out.printf("启动客户端: %s, 间隔: %d\n", job.getKey().getName(), i);

                // 配置任务
                Trigger trigger = TriggerBuilder.newTrigger()
                        .forJob(job)
                        .withSchedule(scheduleBuilder)
                        .build();

                scheduler.scheduleJob(job, trigger);
            }

            // 开启任务
            scheduler.start();
        } catch (SchedulerException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
