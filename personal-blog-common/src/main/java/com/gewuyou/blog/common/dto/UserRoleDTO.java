package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色 DTO
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:05:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Integer id;

    private String roleName;
}
