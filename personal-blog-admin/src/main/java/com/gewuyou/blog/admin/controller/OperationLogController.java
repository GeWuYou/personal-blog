package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.OperationLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.service.IOperationLogService;
import com.gewuyou.blog.common.vo.ConditionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "<p> 操作日志表 前端控制器 </p>", description = "<p> 操作日志表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/operation/logs")
public class OperationLogController {
    private final IOperationLogService operationLogService;

    @Autowired
    public OperationLogController(IOperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    /**
     * 查看操作日志列表
     *
     * @param conditionVO 条件
     * @return 操作日志列表
     */
    @Operation(summary = "查看操作日志列表", description = "查看操作日志列表")
    @GetMapping
    public Result<PageResultDTO<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return Result.success(operationLogService.listOperationLogDTOs(conditionVO));
    }

    /**
     * 删除操作日志
     *
     * @param operationLogIds 操作日志id列表
     * @return 操作结果
     */
    @Operation(summary = "删除操作日志", description = "删除操作日志")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> operationLogIds) {
        operationLogService.removeByIds(operationLogIds);
        return Result.success();
    }
}
