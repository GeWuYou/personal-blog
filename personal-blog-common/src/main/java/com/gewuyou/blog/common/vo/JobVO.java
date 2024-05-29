package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定时任务VO
 *
 * @author gewuyou
 * @since 2024-05-23 下午11:16:24
 */
@Schema(description = "定时任务VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobVO {
    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Integer id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @Schema(description = "调用目标字符串")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @Schema(description = "cron执行表达式")
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @Schema(description = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
    private Integer misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    @Schema(description = "是否并发执行（0允许 1禁止）")
    private Integer concurrent;

    /**
     * 状态（0正常 1暂停）
     */
    @Schema(description = "状态（0正常 1暂停）")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
