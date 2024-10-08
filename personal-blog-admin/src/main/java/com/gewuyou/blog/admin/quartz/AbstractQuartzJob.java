package com.gewuyou.blog.admin.quartz;

import com.gewuyou.blog.admin.mapper.JobLogMapper;
import com.gewuyou.blog.common.constant.ScheduleConstant;
import com.gewuyou.blog.common.model.Job;
import com.gewuyou.blog.common.model.JobLog;
import com.gewuyou.blog.common.utils.ExceptionUtil;
import com.gewuyou.blog.common.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static com.gewuyou.blog.common.constant.CommonConstant.ONE;
import static com.gewuyou.blog.common.constant.CommonConstant.ZERO;

/**
 * 抽象 作业
 *
 * @author gewuyou
 * @since 2024-05-29 下午2:39:00
 */
@Slf4j
public abstract class AbstractQuartzJob implements org.quartz.Job {
    private static final ThreadLocal<Date> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        Job job = new Job();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstant.TASK_PROPERTIES), job);
        try {
            before(context, job);
            doExecute(context, job);
            after(context, job, null);
        } catch (Exception e) {
            log.error("任务执行异常:", e);
            after(context, job, e);
        }
    }


    protected void before(JobExecutionContext context, Job job) {
        THREAD_LOCAL.set(new Date());
    }

    protected void after(JobExecutionContext context, Job job, Exception e) {
        Date startTime = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();
        final JobLog jobLog = new JobLog();
        jobLog.setJobId(job.getId());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        jobLog.setStartTime(startTime);
        jobLog.setEndTime(new Date());
        long runMs = jobLog.getEndTime().getTime() - jobLog.getStartTime().getTime();
        jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLog.setStatus(ZERO);
            jobLog.setExceptionInfo(ExceptionUtil.getTrace(e));
        } else {
            jobLog.setStatus(ONE);
        }
        SpringUtil.getBean(JobLogMapper.class).insert(jobLog);
    }

    protected abstract void doExecute(JobExecutionContext context, Job job) throws Exception;
}
