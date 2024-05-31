package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 照片VO
 *
 * @author gewuyou
 * @since 2024-05-31 下午2:25:15
 */
@Schema(description = "照片VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoVO {
    /**
     * 相册id
     */
    @Schema(description = "相册id")
    @NotNull(message = "相册id不能为空")
    private Integer albumId;


    /**
     * 照片url列表
     */
    @Schema(description = "照片url列表")
    private List<String> photoUrls;


    /**
     * 照片id列表
     */
    @Schema(description = "照片id列表")
    private List<Integer> photoIds;
}
