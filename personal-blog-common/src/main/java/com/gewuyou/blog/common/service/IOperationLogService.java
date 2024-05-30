package com.gewuyou.blog.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.OperationLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.OperationLog;
import com.gewuyou.blog.common.vo.ConditionVO;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IOperationLogService extends IService<OperationLog> {

    /**
     * 分页查询操作日志
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    PageResultDTO<OperationLogDTO> listOperationLogDTOs(ConditionVO conditionVO);
}
