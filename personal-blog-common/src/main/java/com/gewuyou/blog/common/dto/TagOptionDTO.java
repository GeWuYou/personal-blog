package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标记选项 DTO
 *
 * @author gewuyou
 * @since 2024-07-20 下午12:44:20
 */
@Schema(description = "标记选项 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagOptionDTO {
    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long id;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String tagName;
}
