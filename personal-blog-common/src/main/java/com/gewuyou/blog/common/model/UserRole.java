package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 用户角色中间表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_user_role")
@Schema(name = "UserRole对象", description = "用户角色中间表")
@Builder
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户角色中间表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "角色id")
    @TableField("role_id")
    private Integer roleId;
}
