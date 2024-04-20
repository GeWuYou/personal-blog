package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-04-16
 */
@Schema(description = "<p> 文章表 </p>", name = "Article对象")
@Getter
@Setter
@TableName("tb_article")
public class Article implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "articleId", description = "文章id")
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "title", description = "文章标题")
    private String title;

    @Schema(name = "content", description = "文章内容")
    private String content;

    @Schema(name = "views", description = "文章浏览量")
    private Integer views;

    @Schema(name = "status", description = "文章状态 'draft:草稿', 'published:发布', 'deleted:删除' ")
    private String status;

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateTime", description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(name = "publishTime", description = "发布时间")
    private LocalDateTime publishTime;
}
