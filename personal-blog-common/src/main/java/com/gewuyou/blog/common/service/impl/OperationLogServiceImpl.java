package com.gewuyou.blog.common.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.mapper.OperationLogMapper;
import com.gewuyou.blog.common.model.OperationLog;
import com.gewuyou.blog.common.service.IOperationLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
