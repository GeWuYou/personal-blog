package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作业日志搜索 VO
 *
 * @author gewuyou
 * @since 2024-05-30 下午2:58:07
 */
@Schema(description = "作业日志搜索 VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLogSearchVO {
    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Integer jobId;

    /**
     * 任务名
     */
    @Schema(description = "任务名")
    private String jobName;

    /**
     * 任务组
     */
    @Schema(description = "任务组")
    private String jobGroup;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态")
    private Byte status;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;
}
