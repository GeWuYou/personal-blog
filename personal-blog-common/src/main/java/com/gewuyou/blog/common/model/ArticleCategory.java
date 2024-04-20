package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 文章分类中间表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Schema(description = "<p> 文章分类中间表 </p>")
@Getter
@Setter
@TableName("tb_article_category")
public class ArticleCategory implements Serializable {


    @Serial
    @Schema(hidden = true)
    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章Id
     */
    @Schema(description = "文章Id")
    private Long articleId;

    /**
     * 分类Id
     */
    @Schema(description = "分类Id")
    private Long categoryId;
}
