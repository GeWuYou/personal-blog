package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.ExceptionLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.service.IExceptionLogService;
import com.gewuyou.blog.common.vo.ConditionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 异常日志表	 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "异常日志表	 前端控制器", description = "异常日志表	 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/exception/log")
public class ExceptionLogController {
    private final IExceptionLogService exceptionLogService;


    @Autowired
    public ExceptionLogController(IExceptionLogService exceptionLogService) {
        this.exceptionLogService = exceptionLogService;
    }

    /**
     * 获取异常日志列表
     *
     * @param conditionVO 条件
     * @return 异常日志列表
     */
    @Operation(summary = "获取异常日志列表", description = "获取异常日志列表")
    @GetMapping("/list")
    public Result<PageResultDTO<ExceptionLogDTO>> listExceptionLogs(ConditionVO conditionVO) {
        return Result.success(exceptionLogService.listExceptionLogDTOs(conditionVO));
    }

    /**
     * 删除异常日志
     *
     * @param exceptionLogIds 异常日志id列表
     * @return 成功或失败
     */
    @Operation(summary = "删除异常日志", description = "删除异常日志")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    @Idempotent
    public Result<?> deleteExceptionLogs(@RequestBody List<Integer> exceptionLogIds) {
        exceptionLogService.removeByIds(exceptionLogIds);
        return Result.success();
    }
}
