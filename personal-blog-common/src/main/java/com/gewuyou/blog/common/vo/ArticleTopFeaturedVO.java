package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章置顶和推荐VO
 *
 * @author gewuyou
 * @since 2024-05-11 下午9:26:00
 */
@Schema(description = "文章置顶和推荐VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTopFeaturedVO {
    /**
     * 文章id
     */
    @Schema(description = "文章id")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶")
    @NotNull(message = "是否置顶不能为空")
    private Byte isTop;

    /**
     * 是否推荐
     */
    @Schema(description = "是否推荐")
    @NotNull(message = "是否推荐不能为空")
    private Byte isFeatured;
}
