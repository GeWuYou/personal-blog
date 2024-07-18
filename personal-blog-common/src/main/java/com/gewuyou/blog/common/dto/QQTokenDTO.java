package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QQ令牌DTO
 *
 * @author gewuyou
 * @since 2024-07-18 下午12:43:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QQTokenDTO {
    /**
     * QQ令牌
     */
    private String openid;

    /**
     * QQ令牌过期时间
     */
    private String client_id;
}
