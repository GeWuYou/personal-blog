package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_article")
@Schema(name = "Article对象", description = "文章表")
public class Article implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "文章缩略图")
    @TableField("cover")
    private String cover;

    @Schema(description = "文章摘要，如果该字段为空，默认取文章前500个字符作为摘要")
    @TableField("abstract_content")
    private String abstractContent;

    @Schema(description = "文章标题")
    @TableField("title")
    private String title;

    @Schema(description = "文章内容")
    @TableField("content")
    private String content;

    @Schema(description = "浏览次数")
    @TableField("views")
    private Integer views;

    @Schema(description = "文章状态 1公开 2 私密 3草稿")
    @TableField("status")
    private Byte status;

    @Schema(description = "文章类型  1原创 2转载 3翻译")
    @TableField("type")
    private String type;

    @Schema(description = "访问密码")
    @TableField("password")
    private String password;

    @Schema(description = "原文链接")
    @TableField("original_url")
    private String originalUrl;

    @Schema(description = "是否置顶 0 否 1是")
    @TableField("is_top")
    private Byte isTop;

    @Schema(description = "是否推荐 0 否 1是")
    @TableField("is_recommend")
    private Byte isRecommend;

    @Schema(description = "是否删除 0 否 1是")
    @TableField("is_delete")
    private Byte isDelete;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Schema(description = "发布时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;
}
