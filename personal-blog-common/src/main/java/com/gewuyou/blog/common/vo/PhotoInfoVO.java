package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 照片信息 VO
 *
 * @author gewuyou
 * @since 2024-05-31 下午2:20:36
 */
@Schema(description = "照片信息 VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoInfoVO {
    /**
     * 照片id
     */
    @Schema(description = "照片id")
    @NotNull(message = "照片id不能为空")
    private Integer id;

    /**
     * 照片名
     */
    @Schema(description = "照片名")
    @NotBlank(message = "照片名不能为空")
    private String photoName;

    /**
     * 照片描述
     */
    @Schema(description = "照片描述")
    private String photoDesc;
}
