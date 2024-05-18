package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章 Admin DTO
 *
 * @author gewuyou
 * @since 2024-05-12 下午9:15:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文章 Admin DTO")
public class ArticleAdminDTO {
    /**
     * 文章 ID
     */
    @Schema(description = "文章 ID")
    private Long id;

    /**
     * 文章封面
     */
    @Schema(description = "文章封面")
    private String articleCover;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 访问量
     */
    @Schema(description = "访问量")
    private Integer viewsCount;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 标签DTO列表
     */
    @Schema(description = "标签DTO列表")
    private List<TagDTO> tagDTOs;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶")
    private Byte isTop;

    /**
     * 是否推荐
     */
    @Schema(description = "是否推荐")
    private Byte isFeatured;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private Byte isDelete;

    /**
     * 文章状态
     */
    @Schema(description = "文章状态")
    private Byte status;

    /**
     * 文章类型
     */
    @Schema(description = "文章类型")
    private Byte type;
}
