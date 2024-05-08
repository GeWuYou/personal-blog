package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 文章分类中间表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article_category")
@Schema(name = "ArticleCategory对象", description = "文章分类中间表")
public class ArticleCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文章分类中间表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "文章Id")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "分类Id")
    @TableField("category_id")
    private Long categoryId;
}
