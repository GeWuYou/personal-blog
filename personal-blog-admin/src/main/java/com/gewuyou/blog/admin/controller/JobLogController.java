package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IJobLogService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.JobLogDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.JobLogSearchVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 定时任务调度日志表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "<p> 定时任务调度日志表 前端控制器 </p>", description = "<p> 定时任务调度日志表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/jobLog")
public class JobLogController {
    private final IJobLogService jobLogService;

    @Autowired
    public JobLogController(IJobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }

    /**
     * 获取定时任务日志列表
     *
     * @param jobLogSearchVO 定时任务日志搜索条件
     * @return 定时任务日志列表
     */
    @Operation(summary = "获取定时任务日志列表", description = "获取定时任务日志列表")
    @GetMapping("/list")
    public Result<PageResultDTO<JobLogDTO>> listJobLogs(JobLogSearchVO jobLogSearchVO) {
        return Result.success(jobLogService.listJobLogDTOs(jobLogSearchVO));
    }

    /**
     * 删除定时任务日志
     *
     * @param ids 定时任务日志id列表
     * @return 成功或失败
     */
    @Operation(summary = "删除定时任务日志", description = "删除定时任务日志")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    public Result<?> deleteJobLogs(@RequestBody List<Integer> ids) {
        jobLogService.deleteJobLogs(ids);
        return Result.success();
    }

    /**
     * 清空定时任务日志
     *
     * @return 成功或失败
     */
    @Operation(summary = "清空定时任务日志", description = "清空定时任务日志")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/clean")
    public Result<?> cleanJobLogs() {
        jobLogService.cleanJobLogs();
        return Result.success();
    }

    /**
     * 获取定时任务日志的所有组名
     *
     * @return 定时任务日志的所有组名
     */
    @Operation(summary = "获取定时任务日志的所有组名", description = "获取定时任务日志的所有组名")
    @GetMapping("/groups")
    public Result<?> listJobLogGroups() {
        return Result.success(jobLogService.listJobLogGroups());
    }
}
