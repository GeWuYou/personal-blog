package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 分类后台DTO
 *
 * @author gewuyou
 * @since 2024-05-19 下午2:39:53
 */
@Schema(description = "分类后台DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAdminDTO {
    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Long articleCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
}
