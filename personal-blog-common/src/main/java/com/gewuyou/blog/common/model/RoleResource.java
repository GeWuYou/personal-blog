package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 角色资源中间表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_role_resource")
@Schema(name = "RoleResource对象", description = "角色资源中间表")
public class RoleResource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色资源中间表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色id")
    @TableField("role_id")
    private Integer roleId;

    @Schema(description = "资源id")
    @TableField("resource_id")
    private Integer resourceId;
}
