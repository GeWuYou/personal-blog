package com.gewuyou.blog.common.dto;

import com.gewuyou.blog.common.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 文章卡 DTO
 *
 * @author gewuyou
 * @since 2024-05-09 下午7:29:51
 */
@Schema(description = "文章卡 DTO")
@Data
public class ArticleCardDTO {
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
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String articleContent;


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
     * 文章作者
     */
    @Schema(description = "文章作者")
    private AuthorArticleDTO author;

    /**
     * 文章分类名称
     */
    @Schema(description = "文章分类名称")
    private String categoryName;

    /**
     * 文章标签
     */
    @Schema(description = "文章标签")
    private List<Tag> tags;

    /**
     * 文章状态
     */
    @Schema(description = "文章状态")
    private Byte status;

    /**
     * 文章创建时间
     */
    @Schema(description = "文章创建时间")
    private Date createTime;

    /**
     * 文章更新时间
     */
    @Schema(description = "文章更新时间")
    private Date updateTime;
}
