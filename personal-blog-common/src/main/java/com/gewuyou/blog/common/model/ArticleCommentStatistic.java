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
 * 文章评论统计表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_article_comment_statistic")
@Schema(name = "ArticleCommentStatistic对象", description = "文章评论统计表")
public class ArticleCommentStatistic implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章评论量Id")
    @TableId(value = "article_comment_statistic_id", type = IdType.AUTO)
    private Long articleCommentStatisticId;

    @Schema(description = "文章Id")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "评论量")
    @TableField("count")
    private Integer count;

    @Schema(description = "记录时间：每天4点更新一次")
    @TableField("record_date")
    private LocalDateTime recordDate;
}
