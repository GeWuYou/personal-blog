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
 * 照片
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_photo")
@Schema(name = "Photo对象", description = "照片")
public class Photo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "相册id")
    @TableField("album_id")
    private Integer albumId;

    @Schema(description = "照片名")
    @TableField("photo_name")
    private String photoName;

    @Schema(description = "照片描述")
    @TableField("photo_desc")
    private String photoDesc;

    @Schema(description = "照片地址")
    @TableField("photo_src")
    private String photoSrc;

    @Schema(description = "是否删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
