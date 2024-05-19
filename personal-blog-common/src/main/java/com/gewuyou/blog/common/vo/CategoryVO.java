package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gewuyou.blog.common.constant.MessageConstant.CATEGORY_NAME_CANNOT_BE_EMPTY;

/**
 * 类别： VO
 *
 * @author gewuyou
 * @since 2024-05-19 下午3:53:18
 */
@Schema(description = "类别： VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long id;

    /**
     * 分类名
     */
    @Schema(description = "分类名")
    @NotEmpty(message = CATEGORY_NAME_CANNOT_BE_EMPTY)
    private String categoryName;
}
