package com.gewuyou.blog.admin.quartz;

import com.gewuyou.blog.admin.util.JobInvokeUtil;
import com.gewuyou.blog.common.model.Job;
import org.quartz.JobExecutionContext;

/**
 * Quartz 作业执行
 *
 * @author gewuyou
 * @since 2024-05-29 下午3:31:34
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
