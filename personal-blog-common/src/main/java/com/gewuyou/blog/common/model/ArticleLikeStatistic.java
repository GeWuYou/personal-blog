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
 * 文章点赞统计表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_article_like_statistic")
@Schema(name = "ArticleLikeStatistic对象", description = "文章点赞统计表")
public class ArticleLikeStatistic implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章点赞量统计Id")
    @TableId(value = "article__like_statistic_id", type = IdType.AUTO)
    private Long articleLikeStatisticId;

    @Schema(description = "文章Id")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "点赞量")
    @TableField("count")
    private Integer count;

    @Schema(description = "记录时间：每天4点记录一次")
    @TableField("record_date")
    private LocalDateTime recordDate;
}
