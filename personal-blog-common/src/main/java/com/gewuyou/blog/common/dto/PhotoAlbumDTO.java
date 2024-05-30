package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册DTO
 *
 * @author gewuyou
 * @since 2024-05-30 下午10:05:16
 */
@Schema(description = "相册DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumDTO {
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
}
