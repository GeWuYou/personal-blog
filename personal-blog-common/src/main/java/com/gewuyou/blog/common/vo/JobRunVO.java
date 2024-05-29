package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作业运行 VO
 *
 * @author gewuyou
 * @since 2024-05-29 下午9:23:34
 */
@Schema(description = "作业运行 VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRunVO {
    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Integer id;

    /**
     * 任务分组
     */
    @Schema(description = "任务分组")
    private String jobGroup;
}
