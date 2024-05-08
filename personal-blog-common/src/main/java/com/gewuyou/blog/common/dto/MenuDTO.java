package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单 DTO
 *
 * @author gewuyou
 * @since 2024-05-03 下午7:01:34
 */
@Schema(description = "菜单 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    /**
     * 菜单 ID
     */
    @Schema(description = "菜单 ID")
    private Integer id;
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
     * 菜单类型
     */
    @Schema(description = "菜单类型")
    private LocalDateTime createTime;
    /**
     * 菜单排序号
     */

    @Schema(description = "菜单排序号")
    private Integer orderNum;

    /**
     * 菜单是否禁用
     */
    @Schema(description = "菜单是否禁用")
    private Integer isDisable;
    /**
     * 菜单是否隐藏
     */

    @Schema(description = "菜单是否隐藏")
    private Integer isHidden;
    /**
     * 子菜单列表
     */

    @Schema(description = "子菜单列表")
    private List<MenuDTO> children;
}
