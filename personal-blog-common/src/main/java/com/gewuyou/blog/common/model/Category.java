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
 * 分类表
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
@TableName("tb_category")
@Schema(name = "Category对象", description = "分类表")
public class Category extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "分类名")
    @TableField("category_name")
    private String categoryName;

}
