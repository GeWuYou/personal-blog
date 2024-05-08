package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户区域 DTO
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:23:03
 */
@Schema(description = "用户区域 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAreaDTO {
    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    private String name;

    /**
     * 区域值
     */
    @Schema(description = "区域值")
    private Long value;
}
