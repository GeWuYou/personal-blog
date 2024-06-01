package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色DTO
 *
 * @author gewuyou
 * @since 2024-05-31 下午11:12:22
 */
@Schema(description = "角色DTO")
public class RoleDTO {
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

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 是否禁用
     */
    @Schema(description = "是否禁用")
    private Byte isDisable;

    /**
     * 角色拥有的资源ID列表
     */
    @Schema(description = "角色拥有的资源ID列表")
    private List<Integer> resourceIds;

    /**
     * 角色拥有的菜单ID列表
     */
    @Schema(description = "角色拥有的菜单ID列表")
    private List<Integer> menuIds;
}
