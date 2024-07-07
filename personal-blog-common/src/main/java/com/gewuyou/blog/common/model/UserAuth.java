package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户认证信息表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_auth")
@Schema(name = "UserAuth对象", description = "用户认证信息表")
public class UserAuth extends BaseModel implements Serializable {

    @Builder
    public UserAuth(LocalDateTime createTime, LocalDateTime updateTime, Long id, Long userInfoId, String username, String email, String password, Byte loginType, String ipAddress, String ipSource, LocalDateTime lastLoginTime) {
        super(createTime, updateTime);
        this.id = id;
        this.userInfoId = userInfoId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.ipAddress = ipAddress;
        this.ipSource = ipSource;
        this.lastLoginTime = lastLoginTime;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户认证id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户信息id")
    @TableField("user_info_id")
    private Long userInfoId;

    @Schema(description = "用户名")
    @TableField("username")
    private String username;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "登录类型  0表示密码登录 1表示邮箱登录 2表示微信登录 3表示QQ登录")
    @TableField("login_type")
    private Byte loginType;

    @Schema(description = "用户登录ip")
    @TableField("ip_address")
    private String ipAddress;

    @Schema(description = "ip属地")
    @TableField("ip_source")
    private String ipSource;

    @Schema(description = "上次登录时间")
    @TableField("last_login_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;
}
