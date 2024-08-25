package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.JobLogMapper;
import com.gewuyou.blog.admin.service.IJobLogService;
import com.gewuyou.blog.common.dto.JobLogDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.JobLog;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.JobLogSearchVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

    private final Executor asyncTaskExecutor;

    public JobLogServiceImpl(@Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 分页查询定时任务调度日志
     *
     * @param jobLogSearchVO 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<JobLogDTO> listJobLogDTOs(JobLogSearchVO jobLogSearchVO) {
        return CompletableFuture.supplyAsync(() -> baseMapper.selectPage(
                        new Page<>(PageUtil.getCurrent(), PageUtil.getSize()),
                        new LambdaQueryWrapper<JobLog>()
                                .eq(Objects.nonNull(jobLogSearchVO.getJobId()), JobLog::getJobId, jobLogSearchVO.getJobId())
                                .eq(Objects.nonNull(jobLogSearchVO.getStatus()), JobLog::getStatus, jobLogSearchVO.getStatus())
                                .like(StringUtils.isNotBlank(
                                        jobLogSearchVO.getJobName()), JobLog::getJobName, jobLogSearchVO.getJobName())
                                .like(StringUtils.isNotBlank(
                                        jobLogSearchVO.getJobGroup()), JobLog::getJobGroup, jobLogSearchVO.getJobGroup())
                                .between(Objects.nonNull(
                                                jobLogSearchVO.getStartTime()) && Objects.nonNull(jobLogSearchVO.getEndTime()),
                                        JobLog::getCreateTime, jobLogSearchVO.getStartTime(), jobLogSearchVO.getEndTime())
                                .orderByDesc(JobLog::getCreateTime)), asyncTaskExecutor)
                .thenApply(jobLogPage -> new PageResult<>(
                        BeanCopyUtil.copyList(jobLogPage.getRecords(), JobLogDTO.class),
                        jobLogPage.getTotal()))
                .exceptionally(e -> {
                    log.error("查询定时任务调度日志失败", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 批量删除定时任务调度日志
     *
     * @param ids 日志ID列表
     */
    @Override
    public void deleteJobLogs(List<Integer> ids) {
        baseMapper.deleteBatchIds(ids);
    }

    /**
     * 清除定时任务调度日志
     */
    @Override
    public void cleanJobLogs() {
        baseMapper.delete(null);
    }

    /**
     * 获取定时任务日志的所有组名
     *
     * @return 日志组名列表
     */
    @Override
    public List<String> listJobLogGroups() {
        return baseMapper.listJobLogGroups();
    }
}
