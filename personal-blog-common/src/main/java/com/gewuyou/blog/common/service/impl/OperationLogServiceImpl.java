package com.gewuyou.blog.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.OperationLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.mapper.OperationLogMapper;
import com.gewuyou.blog.common.model.OperationLog;
import com.gewuyou.blog.common.service.IOperationLogService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 分页查询操作日志
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Override
    public PageResultDTO<OperationLogDTO> listOperationLogDTOs(ConditionVO conditionVO) {
        Page<OperationLog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        LambdaQueryWrapper<OperationLog> queryWrapper = new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptModule, conditionVO.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptDesc, conditionVO.getKeywords())
                .orderByDesc(OperationLog::getId);
        Page<OperationLog> operationLogPage = this.page(page, queryWrapper);
        List<OperationLogDTO> operationLogDTOs = BeanCopyUtil.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageResultDTO<>(operationLogDTOs, operationLogPage.getTotal());
    }
}
