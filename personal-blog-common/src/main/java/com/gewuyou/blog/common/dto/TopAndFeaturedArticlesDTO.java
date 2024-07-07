package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 热门和精选文章 DTO
 *
 * @author gewuyou
 * @since 2024-05-09 下午7:29:27
 */
@Data
@Schema(description = "热门和精选文章 DTO")
@NoArgsConstructor
@AllArgsConstructor
public class TopAndFeaturedArticlesDTO {
    /**
     * 置顶文章卡片 DTO
     */
    @Schema(description = "置顶文章卡片 DTO")
    private ArticleCardDTO topArticle;

    /**
     * 精选文章卡片 DTO
     */
    @Schema(description = "精选文章卡片 DTO")
    private List<ArticleCardDTO> featuredArticles;
}
