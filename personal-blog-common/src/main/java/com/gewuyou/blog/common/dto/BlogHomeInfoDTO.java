package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客首页 信息 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午6:08:41
 */
@Schema(description = "博客首页 信息 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoDTO {
    /**
     * 文章总数
     */
    @Schema(description = "文章总数")
    private Long articleCount;

    /**
     * 评论总数
     */
    @Schema(description = "评论总数")
    private Long talkCount;

    /**
     * 分类总数
     */
    @Schema(description = "分类总数")
    private Long categoryCount;

    /**
     * 标签总数
     */
    @Schema(description = "标签总数")
    private Long tagCount;

    /**
     * 网站配置DTO
     */
    @Schema(description = "网站配置DTO")
    private WebsiteConfigDTO websiteConfigDTO;

    /**
     * 网站访问量
     */
    @Schema(description = "网站访问量")
    private Long viewCount;
}
