package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Getter
@Setter
@TableName("tb_menu")
@Schema(name = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    @Schema(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父菜单id(默认为0，表示顶级菜单)")
    private Integer parentId;

    @Schema(description = "菜单名")
    private String name;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "菜单组件")
    private String component;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序号")
    private Integer orderNum;

    @Schema(description = "是否禁用 0表示禁用。1表示启用")
    private Long status;

    @Schema(description = "菜单类型，1表示用户后台菜单，2表示管理员后台菜单")
    private Byte type;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}
