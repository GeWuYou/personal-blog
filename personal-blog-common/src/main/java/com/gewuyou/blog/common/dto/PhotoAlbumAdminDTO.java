package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册后台 DTO
 *
 * @author gewuyou
 * @since 2024-05-30 下午7:01:20
 */
@Schema(description = "相册后台 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumAdminDTO {
    /**
     * 相册ID
     */
    @Schema(description = "相册ID")
    private Integer id;

    /**
     * 相册名称
     */
    @Schema(description = "相册名称")
    private String albumName;

    /**
     * 相册描述
     */
    @Schema(description = "相册描述")
    private String albumDesc;

    /**
     * 相册封面
     */
    @Schema(description = "相册封面")
    private String albumCover;

    /**
     * 相片数量
     */
    @Schema(description = "相片数量")
    private Long photoCount;

    /**
     * 状态值
     */
    @Schema(description = "状态值")
    private Byte status;
}
