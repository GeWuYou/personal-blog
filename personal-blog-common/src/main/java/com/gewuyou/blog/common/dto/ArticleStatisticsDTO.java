package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章统计 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午10:34:53
 */
@Schema(description = "文章统计 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleStatisticsDTO {

    /**
     * 日期
     */
    @Schema(description = "日期")
    private String date;

    /**
     * 博客数量
     */
    @Schema(description = "博客数量")
    private Long count;
}
