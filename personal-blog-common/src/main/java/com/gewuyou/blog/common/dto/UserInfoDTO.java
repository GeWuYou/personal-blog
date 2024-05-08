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

/**
 * 用户信息 DTO
 *
 * @author gewuyou
 * @since 2024-04-25 下午6:10:01
 */
@Schema(description = "用户信息 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Integer id;

    /**
     * 用户信息id
     */
    @Schema(description = "用户信息id")
    private Integer userInfoId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String email;

    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private Integer loginType;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 介绍
     */
    @Schema(description = "介绍")
    private String intro;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String website;

    /**
     * 是否订阅
     */
    @Schema(description = "是否订阅")
    private Integer isSubscribe;

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
     * 上次登录时间
     */
    @Schema(description = "上次登录时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @Schema(description = "刷新令牌")
    private String refreshToken;
}
