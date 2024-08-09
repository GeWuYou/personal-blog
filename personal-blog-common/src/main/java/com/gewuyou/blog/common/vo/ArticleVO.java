package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.gewuyou.blog.common.constant.MessageConstant.ARTICLE_CONTENT_NOT_EMPTY;
import static com.gewuyou.blog.common.constant.MessageConstant.ARTICLE_TITLE_NOT_EMPTY;

/**
 * 文章VO
 *
 * @author gewuyou
 * @since 2024-05-06 下午10:11:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文章")
public class ArticleVO {
    @Schema(description = "文章id", type = "Long", example = "1")
    private Long id;

    @NotEmpty(message = ARTICLE_TITLE_NOT_EMPTY)
    @Schema(description = "文章标题", type = "string", example = "Java 8 新特性")
    private String articleTitle;

    @NotEmpty(message = ARTICLE_CONTENT_NOT_EMPTY)
    @Schema(description = "文章内容", type = "string", example = "Java 8 新特性内容")
    private String articleContent;

    @Schema(description = "文章摘要", type = "string", example = "Java 8 新特性摘要")
    private String articleAbstract;

    @Schema(description = "文章缩略图", type = "string", example = "https://xxx.jpg")
    private String articleCover;

    @Schema(description = "文章分类", type = "integer", example = "1")
    private String categoryName;

    @Schema(description = "文章标签", type = "List<String>")
    private List<String> tagNames;

    @Schema(description = "是否置顶", type = "integer", example = "1")
    private Byte isTop;

    @Schema(description = "是否推荐", type = "integer", example = "1")
    private Byte isFeatured;

    @Schema(description = "文章状态", type = "integer", example = "1")
    private Byte status;

    @Schema(description = "文章类型", type = "integer", example = "1")
    private Integer type;

    @Schema(description = "原文链接", type = "string", example = "https://www.gewuyou.com/article/1")
    private String originalUrl;

    @Schema(description = "文章访问密码", type = "string", example = "123456")
    private String password;
}
