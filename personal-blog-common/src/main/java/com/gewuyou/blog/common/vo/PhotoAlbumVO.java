package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册VO
 *
 * @author gewuyou
 * @since 2024-05-30 下午6:26:44
 */
@Schema(description = "相册VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumVO {
    /**
     * 相册Id
     */
    @Schema(description = "相册Id")
    private Integer id;

    /**
     * 相册名
     */
    @Schema(description = "相册名")
    @NotBlank(message = "相册名不能为空")
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
    @NotBlank(message = "相册封面不能为空")
    private String albumCover;

    /**
     * 状态值
     */
    @Schema(description = "状态值")
    private Byte status;
}
