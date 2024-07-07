package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息VO
 *
 * @author gewuyou
 * @since 2024-07-06 上午11:32:59
 */

@Schema(description = "用户信息VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVO {
    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 用户介绍
     */
    @Schema(description = "用户介绍")
    private String intro;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String website;
}
