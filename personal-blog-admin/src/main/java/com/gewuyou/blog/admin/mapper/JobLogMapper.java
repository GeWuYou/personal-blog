package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.JobLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 定时任务调度日志表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {

    /**
     * 获取定时任务日志的所有组名
     *
     * @return 定时任务日志的所有组名
     */
    List<String> listJobLogGroups();
}
