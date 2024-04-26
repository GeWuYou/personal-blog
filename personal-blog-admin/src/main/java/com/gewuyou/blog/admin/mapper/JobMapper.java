package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.Job;
import org.apache.ibatis.annotations.Mapper;

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

}
