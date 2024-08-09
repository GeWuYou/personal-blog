package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 图片引用表
 *
 * @author gewuyou
 * @since 2024-08-09 22:41:44
 */
@Schema(description = "图片引用表")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_image_reference")
@Builder
public class ImageReference {
    @Schema(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 图片路径
     */
    @Schema(description = "图片路径")
    @TableField("image_url")
    private String imageUrl;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    @TableField("is_delete")
    private Byte isDelete;
}
