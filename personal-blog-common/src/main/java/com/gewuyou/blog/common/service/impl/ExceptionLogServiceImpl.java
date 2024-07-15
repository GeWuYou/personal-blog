package com.gewuyou.blog.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.ExceptionLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.mapper.ExceptionLogMapper;
import com.gewuyou.blog.common.model.ExceptionLog;
import com.gewuyou.blog.common.service.IExceptionLogService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.DateUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    /**
     * 查询异常日志列表
     *
     * @param conditionVO 条件
     * @return 异常日志列表
     */
    @Override
    public PageResultDTO<ExceptionLogDTO> listExceptionLogDTOs(ConditionVO conditionVO) {
        Page<ExceptionLog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<ExceptionLog> exceptionLogPage = this.page(page, new LambdaQueryWrapper<ExceptionLog>()
                .like(StringUtils.isNoneBlank(conditionVO.getKeywords()),
                        ExceptionLog::getOptDesc, conditionVO.getKeywords())
                .orderByDesc(ExceptionLog::getId));
        List<ExceptionLogDTO> exceptionLogDTOS = exceptionLogPage
                .getRecords()
                .stream()
                .map(exceptionLog -> {
                    var exceptionLogDTO = BeanCopyUtil.copyObject(exceptionLog, ExceptionLogDTO.class);
                    var createTime = exceptionLog.getCreateTime();
                    if (Objects.nonNull(createTime)) {
                        exceptionLogDTO.setCreateTime(DateUtil.convertToDate(createTime));
                    }
                    return exceptionLogDTO;
                })
                .toList();
        return new PageResultDTO<>(exceptionLogDTOS, exceptionLogPage.getTotal());
    }
}