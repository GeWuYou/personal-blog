package com.gewuyou.blog.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时任务DTO
 *
 * @author gewuyou
 * @since 2024-05-23 下午11:07:01
 */
@Schema(description = "定时任务DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Integer id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String jobName;

    /**
     * 任务分组
     */
    @Schema(description = "任务分组")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @Schema(description = "调用目标字符串")
    private String invokeTarget;

    /**
     * cron表达式
     */
    @Schema(description = "cron表达式")
    private String cronExpression;

    /**
     * 计划执行策略（1立即执行 2执行一次 3放弃执行）
     */
    @Schema(description = "计划执行策略（1立即执行 2执行一次 3放弃执行）")
    private Byte misfirePolicy;

    /**
     * 是否并发（0禁止 1允许）
     */
    @Schema(description = "是否并发（0禁止 1允许）")
    private Byte concurrent;

    /**
     * 任务状态（1正常 0暂停）
     */
    @Schema(description = "任务状态（1正常 0暂停）")
    private Byte status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 备注信息
     */
    @Schema(description = "备注信息")
    private String remark;

    /**
     * 下一次有效时间
     */
    @Schema(description = "下一次有效时间")
    private Date nextValidTime;
}
