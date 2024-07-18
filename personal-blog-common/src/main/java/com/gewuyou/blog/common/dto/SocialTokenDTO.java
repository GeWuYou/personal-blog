package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社交令牌 DTO
 *
 * @author gewuyou
 * @since 2024-07-18 上午11:02:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialTokenDTO {
    /**
     * 唯一标识符
     */
    private String openId;

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * 登录类型
     */
    private Byte loginType;
}
