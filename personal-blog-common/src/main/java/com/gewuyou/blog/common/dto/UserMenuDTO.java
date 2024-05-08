package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户菜单 DTO
 *
 * @author gewuyou
 * @since 2024-04-29 下午11:02:25
 */
@Schema(description = "用户菜单 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuDTO {
    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 菜单路径
     */
    @Schema(description = "菜单路径")
    private String path;
    /**
     * 菜单组件
     */
    @Schema(description = "菜单组件")
    private String component;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Boolean hidden;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    private List<UserMenuDTO> children;
}
