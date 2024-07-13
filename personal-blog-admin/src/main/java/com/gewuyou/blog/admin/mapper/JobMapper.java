package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.JobDTO;
import com.gewuyou.blog.common.model.Job;
import com.gewuyou.blog.common.vo.JobSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 查询定时任务调度列表
     *
     * @param jobSearchVO 定时任务调度搜索条件
     * @return 定时任务调度列表
     */
    Long countJobs(@Param("jobSearchVO") JobSearchVO jobSearchVO);

    /**
     * 查询定时任务调度列表
     *
     * @param page        分页条件
     * @param jobSearchVO 定时任务调度搜索条件
     * @return 定时任务调度列表
     */
    Page<JobDTO> listJobs(Page<JobDTO> page, @Param("jobSearchVO") JobSearchVO jobSearchVO);

    /**
     * 查询定时任务调度组列表
     *
     * @return 定时任务调度组列表
     */
    List<String> listJobGroups();
}
