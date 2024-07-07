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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author gewuyou
 * @since 2024-04-17 下午7:09:34
 */
@Schema(description = "用户信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO implements UserDetails {
    /**
     * 用户认证Id
     */
    @Schema(description = "用户认证id")
    private Long userAuthId;

    /**
     * 用户信息Id
     */
    @Schema(description = "用户信息id")
    private Long userInfoId;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

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
     * 用户最后登录Ip
     */
    @Schema(description = "用户最后登录Ip")
    private String lastIp;


    /**
     * 用户Ip属地
     */
    @Schema(description = "用户Ip属地")
    private String ipSource;

    /**
     * 是否禁用 0表示禁用 1表示启用
     */
    @Schema(description = "是否禁用 0表示禁用 1表示启用")
    private Byte isDisable;

    /**
     * 浏览器
     */
    @Schema(description = "浏览器")
    private String browser;
    /**
     * 登录类型 0表示密码登录 1表示邮箱登录 2表示微信登录 3表示QQ登录
     */
    @Schema(description = "登录类型 0表示密码登录 1表示邮箱登录 2表示微信登录 3表示QQ登录")
    private Byte loginType;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 上次登录时间
     */
    @Schema(description = "上次登录时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;


    /**
     * 登录令牌过期时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;
    /**
     * 用户角色列表
     */
    @Schema(description = "用户角色列表")
    private transient List<String> roles;

    /**
     * 用户点赞的文章列表
     */
    @Schema(description = "用户点赞的文章列表")
    private transient Set<Object> likeArticles;
    /**
     * 用户点赞的评论列表
     */
    @Schema(description = "用户点赞的评论列表")
    private transient Set<Object> likeComments;


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 用户是否未过期
     *
     * @return boolean
     */
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户是否未锁定
     *
     * @return boolean
     */
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭证是否未过期
     *
     * @return boolean
     */
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否处于启用的状态
     *
     * @return boolean
     */
    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    /**
     * 通过判断用户Id、用户名、邮箱是否相同来判断两个用户对象是否相同
     *
     * @param o 用户对象
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        /*
         * 重写比较方法,SpringSecurity根据用户名来比较是否同一个用户
         */
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsDTO that = (UserDetailsDTO) o;
        return Objects.equals(userAuthId, that.userAuthId) && Objects.equals(username, that.username) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAuthId, username, email);
    }
}
