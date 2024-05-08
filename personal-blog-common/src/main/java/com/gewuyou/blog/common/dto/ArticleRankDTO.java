package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章排名 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午11:07:59
 */
@Schema(description = "文章排名 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRankDTO {
    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 文章访问量
     */
    @Schema(description = "文章访问量")
    private Long viewsCount;
}
