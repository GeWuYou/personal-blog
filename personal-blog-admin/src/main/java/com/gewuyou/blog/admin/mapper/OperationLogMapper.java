package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
