package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * 文章表
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article")
@Schema(name = "Article对象", description = "文章表")
public class Article extends BaseModel implements Serializable {
    @Builder
    public Article(LocalDateTime createTime, LocalDateTime updateTime, Long id, Long userId, Long categoryId, String articleCover, String articleAbstract, String articleTitle, String articleContent, Byte status, Byte type, String password, String originalUrl, Byte isTop, Byte isFeatured, Byte isDelete) {
        super(createTime, updateTime);
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.articleCover = articleCover;
        this.articleAbstract = articleAbstract;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.status = status;
        this.type = type;
        this.password = password;
        this.originalUrl = originalUrl;
        this.isTop = isTop;
        this.isFeatured = isFeatured;
        this.isDelete = isDelete;
    }


    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "分类Id")
    @TableField("category_id")
    private Long categoryId;

    @Schema(description = "文章缩略图")
    @TableField("article_cover")
    private String articleCover;

    @Schema(description = "文章摘要，如果该字段为空，默认取文章前500个字符作为摘要")
    @TableField("article_abstract")
    private String articleAbstract;

    @Schema(description = "文章标题")
    @TableField("article_title")
    private String articleTitle;

    @Schema(description = "文章内容")
    @TableField("article_content")
    private String articleContent;

    @Schema(description = "文章状态 1公开 2 私密 3草稿")
    @TableField("status")
    private Byte status;

    @Schema(description = "文章类型  1原创 2转载 3翻译")
    @TableField("type")
    private Byte type;

    @Schema(description = "访问密码")
    @TableField("password")
    private String password;

    @Schema(description = "原文链接")
    @TableField("original_url")
    private String originalUrl;

    @Schema(description = "是否置顶 0 否 1 是")
    @TableField("is_top")
    private Byte isTop;

    @Schema(description = "是否精选 0 否 1 是")
    @TableField("is_featured")
    private Byte isFeatured;

    @Schema(description = "是否删除 0 否 1 是")
    @TableField("is_delete")
    private Byte isDelete;
}
