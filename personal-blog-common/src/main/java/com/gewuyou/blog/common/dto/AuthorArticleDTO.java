package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者文章 DTO
 *
 * @author gewuyou
 * @since 2024-07-19 下午5:06:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "作者文章 DTO")
public class AuthorArticleDTO {
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;
    /**
     * 网站
     */
    @Schema(description = "网站")
    private String website;
    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;
}
