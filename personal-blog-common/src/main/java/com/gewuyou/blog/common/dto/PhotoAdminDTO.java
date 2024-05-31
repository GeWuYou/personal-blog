package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 照片后台DTO
 *
 * @author gewuyou
 * @since 2024-05-31 下午1:27:05
 */
@Schema(description = "照片后台DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAdminDTO {
    /**
     * 照片ID
     */
    @Schema(description = "照片ID")
    private Integer id;

    /**
     * 照片名称
     */
    @Schema(description = "照片名称")
    private String photoName;

    /**
     * 照片描述
     */
    @Schema(description = "照片描述")
    private String photoDesc;

    /**
     * 照片路径
     */
    @Schema(description = "照片路径")
    private String photoSrc;

}
