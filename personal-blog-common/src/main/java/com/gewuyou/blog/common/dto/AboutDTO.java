package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关于DTO
 *
 * @author gewuyou
 * @since 2024-05-05 下午8:45:43
 */
@Schema(description = "关于DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutDTO {
    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;
}
