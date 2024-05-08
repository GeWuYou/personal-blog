package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "用户角色 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Integer id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;
}
