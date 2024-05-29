package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作业状态 VO
 *
 * @author gewuyou
 * @since 2024-05-29 下午9:11:31
 */
@Schema(description = "作业状态 VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusVO {

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Integer id;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态")
    private Byte status;
}
