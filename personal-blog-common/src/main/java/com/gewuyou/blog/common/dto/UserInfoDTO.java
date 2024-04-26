package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息 DTO
 *
 * @author gewuyou
 * @since 2024-04-25 下午6:10:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Integer id;

    private Integer userInfoId;

    private String email;

    private Integer loginType;

    private String username;

    private String nickname;

    private String avatar;

    private String intro;

    private String website;

    private Integer isSubscribe;

    private String ipAddress;

    private String ipSource;

    private LocalDateTime lastLoginTime;
}
