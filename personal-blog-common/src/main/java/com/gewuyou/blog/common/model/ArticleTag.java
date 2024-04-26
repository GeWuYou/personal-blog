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

/**
 * <p>
 * 文章标签中间表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_article_tag")
@Schema(name = "ArticleTag对象", description = "文章标签中间表")
public class ArticleTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章标签中间表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "文章Id")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "标签id")
    @TableField("tag_id")
    private Long tagId;
}
