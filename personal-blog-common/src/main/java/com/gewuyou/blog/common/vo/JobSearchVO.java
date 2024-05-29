package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务搜索 VO
 *
 * @author gewuyou
 * @since 2024-05-29 下午5:42:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "任务搜索 VO")
public class JobSearchVO {
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
     * 任务状态
     */
    @Schema(description = "任务状态")
    private Byte status;
}
