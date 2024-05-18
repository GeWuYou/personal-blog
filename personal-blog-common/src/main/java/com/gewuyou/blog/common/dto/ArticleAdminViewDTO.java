package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台文章DTO
 *
 * @author gewuyou
 * @since 2024-05-13 下午7:00:28
 */
@Schema(description = "文章编辑页码 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAdminViewDTO {
    /**
     * 文章ID
     */
    @Schema(description = "文章ID")
    private Long id;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 文章摘要
     */
    @Schema(description = "文章摘要")
    private String articleAbstract;

    /**
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String articleContent;

    /**
     * 文章封面
     */
    @Schema(description = "文章封面")
    private String articleCover;

    /**
     * 文章分类名
     */
    @Schema(description = "文章分类名")
    private String categoryName;

    /**
     * 文章标签名列表
     */
    @Schema(description = "文章标签名列表")
    private List<String> tagNames;

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
     * 文章状态
     */
    @Schema(description = "文章状态")
    private Byte status;

    /**
     * 文章类型
     */
    @Schema(description = "文章类型")
    private Byte type;

    /**
     * 源链接
     */
    @Schema(description = "源链接")
    private String originalUrl;

    /**
     * 访问密码
     */
    @Schema(description = "访问密码")
    private String password;
}
