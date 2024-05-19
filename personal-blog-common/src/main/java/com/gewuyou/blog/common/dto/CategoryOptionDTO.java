package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类选项 DTO
 *
 * @author gewuyou
 * @since 2024-05-19 下午3:14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "分类选项 DTO")
public class CategoryOptionDTO {
    /**
     * 分类 ID
     */
    @Schema(description = "分类 ID")
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;
}
