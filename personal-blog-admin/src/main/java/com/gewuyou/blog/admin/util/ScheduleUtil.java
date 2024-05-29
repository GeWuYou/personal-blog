package com.gewuyou.blog.admin.util;

import com.gewuyou.blog.admin.quartz.QuartzDisallowConcurrentExecution;
import com.gewuyou.blog.admin.quartz.QuartzJobExecution;
import com.gewuyou.blog.common.constant.ScheduleConstant;
import com.gewuyou.blog.common.enums.JobStatusEnum;
import com.gewuyou.blog.common.exception.TaskException;
import com.gewuyou.blog.common.model.Job;
import org.quartz.*;

/**
 * 调度 工具
 *
 * @author gewuyou
 * @since 2024-05-29 下午2:33:39
 */
public class ScheduleUtil {
    private static Class<? extends org.quartz.Job> getQuartzJobClass(Job job) {
        boolean isConcurrent = Byte.valueOf("1").equals(job.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    public static TriggerKey getTriggerKey(Integer jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstant.TASK_CLASS_NAME + jobId, jobGroup);
    }

    public static JobKey getJobKey(Integer jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstant.TASK_CLASS_NAME + jobId, jobGroup);
    }

    public static void createScheduleJob(Scheduler scheduler, Job job) throws SchedulerException, TaskException {
        Class<? extends org.quartz.Job> jobClass = getQuartzJobClass(job);
        Integer jobId = job.getId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();
        jobDetail.getJobDataMap().put(ScheduleConstant.TASK_PROPERTIES, job);
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }
        scheduler.scheduleJob(jobDetail, trigger);
        if (job.getStatus().equals(JobStatusEnum.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        }
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(Job job, CronScheduleBuilder cb) throws TaskException {
        return switch (job.getMisfirePolicy()) {
            case ScheduleConstant.MISFIRE_DEFAULT -> cb;
            case ScheduleConstant.MISFIRE_IGNORE_MISFIRES -> cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstant.MISFIRE_FIRE_AND_PROCEED -> cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstant.MISFIRE_DO_NOTHING -> cb.withMisfireHandlingInstructionDoNothing();
            default -> throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                    + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        };
    }
}
