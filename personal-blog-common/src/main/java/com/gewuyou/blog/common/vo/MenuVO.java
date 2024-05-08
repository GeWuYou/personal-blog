package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单 VO
 *
 * @author gewuyou
 * @since 2024-05-03 下午8:13:20
 */
@Schema(description = "菜单 VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuVO {
    /**
     * 菜单id
     */
    @Schema(description = "菜单id")
    private Integer id;

    /**
     * 菜单名
     */
    @Schema(description = "菜单名")
    @NotNull(message = "菜单名不能为空")
    private String name;

    /**
     * 菜单icon
     */
    @Schema(description = "菜单icon")
    @NotNull(message = "菜单icon不能为空")
    private String icon;


    /**
     * 路径
     */
    @Schema(description = "路径")
    @NotNull(message = "路径不能为空")
    private String path;

    /**
     * 组件
     */
    @Schema(description = "组件")
    @NotNull(message = "组件不能为空")
    private String component;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    /**
     * 父id
     */
    @Schema(description = "父id")
    private Integer parentId;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Integer isHidden;
}
