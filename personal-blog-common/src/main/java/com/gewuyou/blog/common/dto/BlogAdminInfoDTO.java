package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 博客管理员信息 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午10:08:46
 */
@Schema(description = "博客管理员信息 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogAdminInfoDTO {
    /**
     * 访问量
     */
    @Schema(description = "访问量")
    private Long viewsCount;

    /**
     * 评论数量
     */
    @Schema(description = "评论数量")
    private Long messageCount;

    /**
     * 用户数量
     */
    @Schema(description = "用户数量")
    private Long userCount;

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Long articleCount;

    /**
     * 分类DTO列表
     */
    @Schema(description = "分类DTO列表")
    private List<CategoryDTO> categoryDTOs;

    /**
     * 标签DTO列表
     */
    @Schema(description = "标签DTO列表")
    private List<TagDTO> tagDTOs;

    /**
     * 文章统计DTO列表
     */
    @Schema(description = "文章统计DTO列表")
    private List<ArticleStatisticsDTO> articleStatisticsDTOs;

    /**
     * 访问量统计DTO列表
     */
    @Schema(description = "访问量统计DTO列表")
    private List<UniqueViewDTO> uniqueViewDTOs;

    /**
     * 文章排行DTO列表
     */
    @Schema(description = "文章排行DTO列表")
    private List<ArticleRankDTO> articleRankDTOs;
}
