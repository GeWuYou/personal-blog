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
 * 定时任务调度表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_job")
@Schema(name = "Job对象", description = "定时任务调度表")
public class Job implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "任务名称")
    @TableField("job_name")
    private String jobName;

    @Schema(description = "任务组名")
    @TableField("job_group")
    private String jobGroup;

    @Schema(description = "调用目标字符串")
    @TableField("invoke_target")
    private String invokeTarget;

    @Schema(description = "cron执行表达式")
    @TableField("cron_expression")
    private String cronExpression;

    @Schema(description = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
    @TableField("misfire_policy")
    private Boolean misfirePolicy;

    @Schema(description = "是否并发执行（0禁止 1允许）")
    @TableField("concurrent")
    private Boolean concurrent;

    @Schema(description = "状态（0暂停 1正常）")
    @TableField("status")
    private Boolean status;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Schema(description = "备注信息")
    @TableField("remark")
    private String remark;
}
