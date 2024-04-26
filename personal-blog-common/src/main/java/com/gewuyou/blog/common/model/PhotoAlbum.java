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
 * 相册
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_photo_album")
@Schema(name = "PhotoAlbum对象", description = "相册")
public class PhotoAlbum implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "相册名")
    @TableField("album_name")
    private String albumName;

    @Schema(description = "相册描述")
    @TableField("album_desc")
    private String albumDesc;

    @Schema(description = "相册封面")
    @TableField("album_cover")
    private String albumCover;

    @Schema(description = "是否删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @Schema(description = "状态值 1公开 2私密")
    @TableField("status")
    private Boolean status;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
