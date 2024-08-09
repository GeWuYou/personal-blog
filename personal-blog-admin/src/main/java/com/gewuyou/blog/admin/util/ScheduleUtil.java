package com.gewuyou.blog.admin.util;

import com.gewuyou.blog.admin.quartz.QuartzDisallowConcurrentExecution;
import com.gewuyou.blog.admin.quartz.QuartzJobExecution;
import com.gewuyou.blog.common.constant.ScheduleConstant;
import com.gewuyou.blog.common.enums.JobStatusEnum;
import com.gewuyou.blog.common.exception.TaskException;
import com.gewuyou.blog.common.model.Job;
import org.quartz.*;

/**
 * 调度工具类
 * <p>
 * 该类提供了用于管理 Quartz 任务调度的方法，包括创建和管理定时任务。
 * 主要功能包括获取任务的触发器和作业的键值、创建新的定时任务、以及处理任务的失火策略。
 *
 * @author gewuyou
 * @since 2024-05-29 下午2:33:39
 */
public class ScheduleUtil {

    /**
     * 根据任务的并发设置，获取 Quartz 任务类的类型。
     * 如果任务允许并发执行，返回 {@link QuartzJobExecution}；
     * 否则，返回 {@link QuartzDisallowConcurrentExecution}。
     *
     * @param job 任务信息
     * @return Quartz 任务类的类型
     */
    private static Class<? extends org.quartz.Job> getQuartzJobClass(Job job) {
        boolean isConcurrent = Byte.valueOf("1").equals(job.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 获取触发器的唯一标识。
     *
     * @param jobId    任务ID
     * @param jobGroup 任务组名
     * @return 触发器的唯一标识 {@link TriggerKey}
     */
    public static TriggerKey getTriggerKey(Integer jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstant.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 获取作业的唯一标识。
     *
     * @param jobId    任务ID
     * @param jobGroup 任务组名
     * @return 作业的唯一标识 {@link JobKey}
     */
    public static JobKey getJobKey(Integer jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstant.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建一个定时任务。
     * <p>
     * 该方法会先检查任务是否存在，若存在则删除原任务，并根据新的任务信息创建一个新的定时任务。
     * 如果任务状态为暂停状态，则在任务创建后立即暂停任务。
     *
     * @param scheduler 调度器 {@link Scheduler}
     * @param job       任务信息
     * @throws SchedulerException 调度器异常
     * @throws TaskException      任务配置异常
     */
    public static void createScheduleJob(Scheduler scheduler, Job job) throws SchedulerException, TaskException {
        Class<? extends org.quartz.Job> jobClass = getQuartzJobClass(job);
        Integer jobId = job.getId();
        String jobGroup = job.getJobGroup();

        // 构建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(getJobKey(jobId, jobGroup))
                .build();

        // 构建Cron触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder)
                .build();

        // 将任务的其他数据放入JobDataMap中
        jobDetail.getJobDataMap().put(ScheduleConstant.TASK_PROPERTIES, job);

        // 检查任务是否存在，若存在则删除
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);

        // 如果任务状态为暂停状态，则暂停任务
        if (job.getStatus().equals(JobStatusEnum.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * 处理任务的失火策略。
     * <p>
     * 根据任务配置的失火策略，配置相应的 CronScheduleBuilder。
     *
     * @param job 任务信息
     * @param cb  Cron 调度构建器 {@link CronScheduleBuilder}
     * @return 配置了失火策略的 CronScheduleBuilder
     * @throws TaskException 如果配置了不支持的失火策略，则抛出任务配置异常
     */
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
