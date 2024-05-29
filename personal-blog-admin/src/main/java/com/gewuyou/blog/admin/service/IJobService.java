package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.JobDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.Job;
import com.gewuyou.blog.common.vo.JobRunVO;
import com.gewuyou.blog.common.vo.JobSearchVO;
import com.gewuyou.blog.common.vo.JobStatusVO;
import com.gewuyou.blog.common.vo.JobVO;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IJobService extends IService<Job> {

    /**
     * 保存定时任务
     *
     * @param jobVO 定时任务信息
     */
    void saveJob(JobVO jobVO);

    /**
     * 更新定时任务
     *
     * @param jobVO 定时任务信息
     */
    void updateJob(JobVO jobVO);

    /**
     * 删除定时任务
     *
     * @param jobIds 定时任务id列表
     */
    void deleteJobs(List<Integer> jobIds);

    /**
     * 根据Id获取定时任务
     *
     * @param jobId 定时任务id
     * @return 定时任务信息
     */
    JobDTO getJobDTOById(Integer jobId);

    /**
     * 分页查询定时任务列表
     *
     * @param jobSearchVO 定时任务查询条件
     * @return 定时任务列表
     */
    PageResultDTO<JobDTO> listJobDTOs(JobSearchVO jobSearchVO);

    /**
     * 更新定时任务状态
     *
     * @param jobStatusVO 定时任务状态信息
     */
    void updateJobStatus(JobStatusVO jobStatusVO);

    /**
     * 运行定时任务
     *
     * @param jobRunVO 定时任务运行信息
     */
    void runJob(JobRunVO jobRunVO);

    /**
     * 获取所有定时任务组名
     *
     * @return 定时任务组名列表
     */
    List<String> listJobGroups();
}
