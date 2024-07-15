package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IsHiddenVO
 *
 * @author gewuyou
 * @since 2024-05-03 下午8:35:27
 */
@Schema(description = "IsHiddenVO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsHiddenVO {
    /**
     * id
     */
    @Schema(description = "id")
    private Integer id;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Byte isHidden;
}
