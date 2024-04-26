package com.gewuyou.blog.admin.service.impl;

import com.gewuyou.blog.admin.mapper.JobMapper;
import com.gewuyou.blog.admin.service.IJobService;
import com.gewuyou.blog.common.model.Job;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

}
