package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * 定时任务调度表
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_job")
@Schema(name = "Job对象", description = "定时任务调度表")
public class Job extends BaseModel implements Serializable {
    @Builder
    public Job(LocalDateTime createTime, LocalDateTime updateTime, Integer id, String jobName, String jobGroup, String invokeTarget, String cronExpression, Byte misfirePolicy, Byte concurrent, Byte status, String remark) {
        super(createTime, updateTime);
        this.id = id;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.invokeTarget = invokeTarget;
        this.cronExpression = cronExpression;
        this.misfirePolicy = misfirePolicy;
        this.concurrent = concurrent;
        this.status = status;
        this.remark = remark;
    }


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
    private Byte misfirePolicy;

    @Schema(description = "是否并发执行（0禁止 1允许）")
    @TableField("concurrent")
    private Byte concurrent;

    @Schema(description = "状态（0暂停 1正常）")
    @TableField("status")
    private Byte status;

    @Schema(description = "备注信息")
    @TableField("remark")
    private String remark;

    @TableField(exist = false)
    private Date nextValidTime;
}
