package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IJobService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.JobDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.JobRunVO;
import com.gewuyou.blog.common.vo.JobSearchVO;
import com.gewuyou.blog.common.vo.JobStatusVO;
import com.gewuyou.blog.common.vo.JobVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "<p> 定时任务调度表 前端控制器 </p>", description = "<p> 定时任务调度表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/job")
public class JobController {
    private final IJobService jobService;

    @Autowired
    public JobController(IJobService jobService) {
        this.jobService = jobService;
    }

    /**
     * 添加定时任务
     *
     * @param jobVO 定时任务VO
     * @return Result
     */
    @Operation(summary = "添加定时任务", description = "添加定时任务")
    @OperationLogging(type = OperationType.SAVE)
    @PostMapping
    public Result<?> saveJob(@RequestBody JobVO jobVO) {
        jobService.saveJob(jobVO);
        return Result.success();
    }

    /**
     * 更新定时任务
     *
     * @param jobVO 定时任务VO
     * @return Result
     */
    @Operation(summary = "更新定时任务", description = "更新定时任务")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping
    public Result<?> updateJob(@RequestBody JobVO jobVO) {
        jobService.updateJob(jobVO);
        return Result.success();
    }

    /**
     * 删除定时任务
     *
     * @param jobIds 定时任务id列表
     * @return Result
     */
    @Operation(summary = "删除定时任务", description = "删除定时任务")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    public Result<?> deleteJobById(@RequestBody List<Integer> jobIds) {
        jobService.deleteJobs(jobIds);
        return Result.success();
    }

    /**
     * 根据ID获取定时任务
     *
     * @param jobId 定时任务ID
     * @return Result
     */
    @Parameter(name = "jobId", description = "定时任务ID", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据ID获取定时任务", description = "根据ID获取定时任务")
    @GetMapping("/{id}")
    public Result<JobDTO> getJobById(@PathVariable("id") Integer jobId) {
        return Result.success(jobService.getJobDTOById(jobId));
    }

    /**
     * 获取任务列表
     *
     * @param jobSearchVO 定时任务搜索条件
     * @return Result
     */
    @Operation(summary = "获取任务列表", description = "获取任务列表")
    @GetMapping("/list")
    public Result<PageResultDTO<JobDTO>> listJobs(JobSearchVO jobSearchVO) {
        return Result.success(jobService.listJobDTOs(jobSearchVO));
    }

    /**
     * 更新定时任务状态
     *
     * @param jobStatusVO 定时任务状态VO
     * @return Result
     */
    @Operation(summary = "更新定时任务状态", description = "更新定时任务状态")
    @PutMapping("/status")
    public Result<?> updateJobStatus(@RequestBody JobStatusVO jobStatusVO) {
        jobService.updateJobStatus(jobStatusVO);
        return Result.success();
    }

    /**
     * 执行某个任务
     *
     * @param jobRunVO 定时任务运行VO
     * @return Result
     */
    @Operation(summary = "执行某个任务", description = "执行某个任务")
    @PutMapping("/run")
    public Result<?> runJob(@RequestBody JobRunVO jobRunVO) {
        jobService.runJob(jobRunVO);
        return Result.success();
    }

    /**
     * 获取所有任务组
     *
     * @return Result
     */
    @Operation(summary = "获取所有任务组", description = "获取所有任务组")
    @GetMapping("/groups")
    public Result<List<String>> listJobGroup() {
        return Result.success(jobService.listJobGroups());
    }
}
