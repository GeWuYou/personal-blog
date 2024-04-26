package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ExceptionLogMapper;
import com.gewuyou.blog.admin.service.IExceptionLogService;
import com.gewuyou.blog.common.model.ExceptionLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 异常日志表	 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements IExceptionLogService {

}
