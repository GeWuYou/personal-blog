package com.gewuyou.blog.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.ExceptionLogDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.ExceptionLog;
import com.gewuyou.blog.common.vo.ConditionVO;

/**
 * <p>
 * 异常日志表	 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IExceptionLogService extends IService<ExceptionLog> {

    /**
     * 查询异常日志列表
     *
     * @param conditionVO 条件
     * @return 异常日志列表
     */
    PageResult<ExceptionLogDTO> listExceptionLogDTOs(ConditionVO conditionVO);

    /**
     * 清除异常日志
     */
    void cleanExceptionLogs();
}
