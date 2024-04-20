package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Getter
@Setter
@TableName("tb_user")
@Schema(name = "User对象", description = "用户表")
@Builder
public class User implements Serializable {

    @Schema(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户Id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "密码")
    private String passWord;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否禁用 0表示禁用 1表示启用")
    private boolean status;

    @Schema(description = "登录类型  0表示密码登录 1表示邮箱登录 2表示微信登录 3表示QQ登录")
    private Integer loginType;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "Ip属地")
    private String ipSource;

    @Schema(description = "上次登录Ip")
    private String lastIp;

    @Schema(description = "注册时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "上次登录时间")
    private LocalDateTime lastLoginTime;
}
