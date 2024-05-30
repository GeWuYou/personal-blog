package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.JobLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.JobLog;
import com.gewuyou.blog.common.vo.JobLogSearchVO;

import java.util.List;

/**
 * <p>
 * 定时任务调度日志表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IJobLogService extends IService<JobLog> {

    /**
     * 分页查询定时任务调度日志
     *
     * @param jobLogSearchVO 查询条件
     * @return 分页结果
     */
    PageResultDTO<JobLogDTO> listJobLogDTOs(JobLogSearchVO jobLogSearchVO);

    /**
     * 批量删除定时任务调度日志
     *
     * @param ids 日志ID列表
     */
    void deleteJobLogs(List<Integer> ids);

    /**
     * 清除定时任务调度日志
     */
    void cleanJobLogs();

    /**
     * 获取定时任务日志的所有组名
     *
     * @return 日志组名列表
     */
    List<String> listJobLogGroups();
}
