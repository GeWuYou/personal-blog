package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.JobMapper;
import com.gewuyou.blog.admin.service.IJobService;
import com.gewuyou.blog.admin.util.ScheduleUtil;
import com.gewuyou.blog.common.dto.JobDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.JobStatusEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.exception.TaskException;
import com.gewuyou.blog.common.model.Job;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.CronUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.JobRunVO;
import com.gewuyou.blog.common.vo.JobSearchVO;
import com.gewuyou.blog.common.vo.JobStatusVO;
import com.gewuyou.blog.common.vo.JobVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
@Slf4j
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {


    private final Scheduler scheduler;
    private final Executor asyncTaskExecutor;

    @Autowired
    public JobServiceImpl(Scheduler scheduler, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.scheduler = scheduler;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 保存定时任务
     *
     * @param jobVO 定时任务信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(JobVO jobVO) {
        checkCronIsValid(jobVO);
        Job job = BeanCopyUtil.copyObject(jobVO, Job.class);
        int row = baseMapper.insert(job);
        if (row > 0) {
            try {
                ScheduleUtil.createScheduleJob(scheduler, job);
            } catch (SchedulerException | TaskException e) {
                throw new GlobalException(ResponseInformation.CREATE_TASK_FAILED);
            }
        }
    }

    /**
     * 更新定时任务
     *
     * @param jobVO 定时任务信息
     */
    @Override
    public void updateJob(JobVO jobVO) {
        checkCronIsValid(jobVO);
        Job oldJob = baseMapper.selectById(jobVO.getId());
        Job newJob = BeanCopyUtil.copyObject(jobVO, Job.class);
        int row = baseMapper.updateById(newJob);
        if (row > 0) {
            updateSchedulerJob(newJob, oldJob.getJobGroup());
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobIds 定时任务id列表
     */
    @Override
    public void deleteJobs(List<Integer> jobIds) {
        List<Job> jobs = baseMapper.selectList(
                new LambdaQueryWrapper<Job>()
                        .in(Job::getId, jobIds)
        );
        int row = baseMapper.deleteBatchIds(jobIds);
        if (row > 0) {
            jobs.forEach(job -> {
                try {
                    scheduler.deleteJob(ScheduleUtil.getJobKey(job.getId(), job.getJobGroup()));
                } catch (SchedulerException e) {
                    throw new GlobalException(ResponseInformation.DELETE_TASK_FAILED);
                }
            });
        }
    }

    /**
     * 根据Id获取定时任务
     *
     * @param jobId 定时任务id
     * @return 定时任务信息
     */
    @Override
    public JobDTO getJobDTOById(Integer jobId) {
        Job job = baseMapper.selectById(jobId);
        JobDTO jobDTO = BeanCopyUtil.copyObject(job, JobDTO.class);
        Date nextExecution = CronUtil.getNextExecution(jobDTO.getCronExpression());
        jobDTO.setNextValidTime(nextExecution);
        return jobDTO;
    }

    /**
     * 分页查询定时任务列表
     *
     * @param jobSearchVO 定时任务查询条件
     * @return 定时任务列表
     */
    @Override
    public PageResult<JobDTO> listJobDTOs(JobSearchVO jobSearchVO) {
        return CompletableFuture.supplyAsync(() ->
                                baseMapper.listJobDTOs(new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize()), jobSearchVO)
                        , asyncTaskExecutor)
                .thenApply(PageResult::new)
                .exceptionally(e -> {
                    log.error("分页查询定时任务列表失败", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 更新定时任务状态
     *
     * @param jobStatusVO 定时任务状态信息
     */
    @Override
    public void updateJobStatus(JobStatusVO jobStatusVO) {
        Job job = baseMapper.selectById(jobStatusVO.getId());
        Byte status = jobStatusVO.getStatus();
        Integer id = job.getId();
        String jobGroup = job.getJobGroup();
        if (job.getStatus().equals(status)) {
            return;
        }
        LambdaUpdateWrapper<Job> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Job::getId, jobStatusVO.getId())
                .set(Job::getStatus, status);
        int row = baseMapper.update(null, updateWrapper);
        if (row > 0) {
            try {
                if (JobStatusEnum.NORMAL.getValue().equals(status)) {
                    scheduler.resumeJob(ScheduleUtil.getJobKey(id, jobGroup));
                } else if (JobStatusEnum.PAUSE.getValue().equals(status)) {
                    scheduler.pauseJob(ScheduleUtil.getJobKey(id, jobGroup));
                }
            } catch (SchedulerException e) {
                throw new GlobalException(ResponseInformation.DELETE_TASK_FAILED);
            }
        }
    }

    /**
     * 运行定时任务
     *
     * @param jobRunVO 定时任务运行信息
     */
    @Override
    public void runJob(JobRunVO jobRunVO) {
        Integer jobId = jobRunVO.getId();
        String jobGroup = jobRunVO.getJobGroup();
        try {
            scheduler.triggerJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        } catch (Exception e) {
            log.error("运行定时任务失败", e);
            throw new GlobalException(ResponseInformation.RUN_TASK_FAILED);
        }
    }

    /**
     * 获取所有定时任务组名
     *
     * @return 定时任务组名列表
     */
    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<List<String>> listJobGroups() {
        return CompletableFuture.completedFuture(baseMapper.listJobGroups());
    }

    /**
     * 检查Cron表达式是否有效
     *
     * @param jobVO 定时任务VO
     */
    private void checkCronIsValid(JobVO jobVO) {
        boolean valid = CronUtil.isValid(jobVO.getCronExpression());
        Assert.isTrue(valid, "Cron表达式无效!");
    }

    /**
     * 更新调度器任务
     *
     * @param job      定时任务
     * @param jobGroup 定时任务分组
     */
    private void updateSchedulerJob(Job job, String jobGroup) {
        Integer jobId = job.getId();
        JobKey jobKey = ScheduleUtil.getJobKey(jobId, jobGroup);
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
            ScheduleUtil.createScheduleJob(scheduler, job);
        } catch (SchedulerException | TaskException e) {
            throw new GlobalException(ResponseInformation.CREATE_TASK_FAILED);
        }
    }
}
