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
 * 文章访问统计表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Getter
@Setter
@TableName("tb_article_view_statistic")
@Schema(name = "ArticleViewStatistic对象", description = "文章访问统计表")
public class ArticleViewStatistic implements Serializable {

    @Schema(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章访问量Id")
    @TableId(value = "article_view_statistic_id", type = IdType.AUTO)
    private Long articleViewStatisticId;

    @Schema(description = "文章Id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "文章访问次数")
    private Integer count;

    @Schema(description = "记录时间：每天4点记录一次")
    private LocalDateTime recordDate;
}
