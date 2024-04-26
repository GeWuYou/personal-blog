package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.ExceptionLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 异常日志表Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

}
