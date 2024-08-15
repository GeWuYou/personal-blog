package com.gewuyou.blog.admin.initializer;

import com.gewuyou.blog.admin.service.IJobService;
import com.gewuyou.blog.admin.util.ScheduleUtil;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 计划任务初始化
 *
 * @author gewuyou
 * @since 2024-08-15 12:04:20
 */
@Component
@Slf4j
public class ScheduleJobInitializer {
    private final IJobService jobService;
    private final Scheduler scheduler;

    public ScheduleJobInitializer(IJobService jobService, Scheduler scheduler) {
        this.jobService = jobService;
        this.scheduler = scheduler;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            // 从数据库加载所有的定时任务
            List<Job> jobList = jobService.list();

            for (Job job : jobList) {
                // 注册定时任务
                ScheduleUtil.createScheduleJob(scheduler, job);
            }
            log.info("初始化定时任务完成!");
        } catch (Exception e) {
            // 处理异常
            throw new GlobalException(ResponseInformation.INIT_SCHEDULE_JOB_FAILED);
        }
    }
}
