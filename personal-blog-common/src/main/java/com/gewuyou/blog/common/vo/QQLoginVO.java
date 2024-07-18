package com.gewuyou.blog.common.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QQ登录VO
 *
 * @author gewuyou
 * @since 2024-07-18 上午9:58:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QQLoginVO {
    /**
     * QQ用户的唯一标识
     */
    @NotBlank(message = "openId不能为空")
    private String openId;

    /**
     * QQ用户的access token
     */
    @NotBlank(message = "accessToken不能为空")
    private String accessToken;
}
