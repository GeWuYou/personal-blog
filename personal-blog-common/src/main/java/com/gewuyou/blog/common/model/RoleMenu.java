package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * 角色菜单中间表
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_role_menu")
@Schema(name = "RoleMenu对象", description = "角色菜单中间表")
public class RoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色菜单中间表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色id")
    @TableField("role_id")
    private Integer roleId;

    @Schema(description = "菜单id")
    @TableField("menu_id")
    private Integer menuId;
}
