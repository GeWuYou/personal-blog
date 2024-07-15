package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gewuyou.blog.common.constant.MessageConstant.Tag_NOT_EMPTY;

/**
 * 标签VO
 *
 * @author gewuyou
 * @since 2024-06-02 下午3:08:42
 */
@Schema(description = "标签VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {
    /**
     * 标签id
     */

    @Schema(description = "标签id")
    private Long id;

    /**
     * 标签名
     */
    @Schema(description = "标签名")
    @NotBlank(message = Tag_NOT_EMPTY)
    private String tagName;
}
