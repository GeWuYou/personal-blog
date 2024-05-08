package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类别 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午10:25:24
 */
@Schema(description = "类别 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    /**
     * 类别 ID
     */
    @Schema(description = "类别 ID")
    private Long id;

    /**
     * 类别名称
     */
    @Schema(description = "类别名称")
    private String categoryName;

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Long articleCount;
}
