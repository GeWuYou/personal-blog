package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用户管理员 DTO
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:04:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminDTO {
    private Integer id;

    private Integer userInfoId;

    private String avatar;

    private String nickname;

    private List<UserRoleDTO> roles;

    private Integer loginType;

    private String ipAddress;

    private String ipSource;

    private Date createTime;

    private Date lastLoginTime;

    private Integer isDisable;

    private Integer status;
}
