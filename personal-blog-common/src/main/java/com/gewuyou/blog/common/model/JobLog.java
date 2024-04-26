package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 定时任务调度日志表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_job_log")
@Schema(name = "JobLog对象", description = "定时任务调度日志表")
public class JobLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务日志ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "任务ID")
    @TableField("job_id")
    private Integer jobId;

    @Schema(description = "任务名称")
    @TableField("job_name")
    private String jobName;

    @Schema(description = "任务组名")
    @TableField("job_group")
    private String jobGroup;

    @Schema(description = "调用目标字符串")
    @TableField("invoke_target")
    private String invokeTarget;

    @Schema(description = "日志信息")
    @TableField("job_message")
    private String jobMessage;

    @Schema(description = "执行状态（0正常 1失败）")
    @TableField("status")
    private Boolean status;

    @Schema(description = "异常信息")
    @TableField("exception_info")
    private String exceptionInfo;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;
}
