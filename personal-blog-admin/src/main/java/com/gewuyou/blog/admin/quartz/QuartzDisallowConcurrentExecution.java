package com.gewuyou.blog.admin.quartz;

import com.gewuyou.blog.admin.util.JobInvokeUtil;
import com.gewuyou.blog.common.model.Job;
import org.quartz.JobExecutionContext;

/**
 * Quartz 不允许并发执行的任务
 *
 * @author gewuyou
 * @since 2024-05-29 下午3:56:47
 */
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
