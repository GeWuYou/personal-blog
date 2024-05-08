package com.gewuyou.blog.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户管理员 DTO
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:04:26
 */
@Schema(description = "用户管理员 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminDTO {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 用户信息id
     */
    @Schema(description = "用户信息id")
    private Integer userInfoId;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<UserRoleDTO> roles;

    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private Integer loginType;

    /**
     * ip地址
     */
    @Schema(description = "ip地址")
    private String ipAddress;

    /**
     * ip属地
     */
    @Schema(description = "ip属地")
    private String ipSource;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;


    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;

    /**
     * 是否禁用
     */
    @Schema(description = "是否禁用")
    private Integer isDisable;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Integer status;
}
