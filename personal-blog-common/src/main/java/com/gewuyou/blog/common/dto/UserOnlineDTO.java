package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户在线DTO
 *
 * @author gewuyou
 * @since 2024-07-04 下午10:04:49
 */
@Schema(description = "用户在线DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOnlineDTO {
    /**
     * 用户信息ID
     */
    @Schema(description = "用户信息ID")
    private Long userInfoId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String ipAddress;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    private String ipSource;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 上次登录时间
     */
    @Schema(description = "上次登录时间")
    private Date lastLoginTime;
}
