package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.JobLogMapper;
import com.gewuyou.blog.admin.service.IJobLogService;
import com.gewuyou.blog.common.model.JobLog;
import org.springframework.stereotype.Service;

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

}
