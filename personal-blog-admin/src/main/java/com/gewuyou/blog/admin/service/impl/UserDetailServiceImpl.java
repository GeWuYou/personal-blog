package com.gewuyou.blog.admin.service.impl;

import com.gewuyou.blog.admin.mapper.RoleMapper;
import com.gewuyou.blog.admin.mapper.UserMapper;
import com.gewuyou.blog.common.dto.UserDetailDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.User;
import com.gewuyou.blog.common.utils.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详情实现类
 *
 * @author gewuyou
 * @since 2024-04-17 下午2:40:16
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final HttpServletRequest request;

    private final RoleMapper roleMapper;

    @Autowired
    public UserDetailServiceImpl(
            UserMapper userMapper,
            RoleMapper roleMapper,
            HttpServletRequest request) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.request = request;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
            throw new GlobalException(ResponseInformation.USERNAME_LOGIN_ERROR);
        }
        EmailValidator validator = EmailValidator.getInstance();
        User user;
        // 是邮箱还是用户名
        if (validator.isValid(usernameOrEmail)) {
            user = userMapper.selectByUserName(usernameOrEmail)
                    .orElseThrow(() -> new GlobalException(ResponseInformation.USERNAME_LOGIN_ERROR));
        } else {
            user = userMapper.selectByEmail(usernameOrEmail)
                    .orElseThrow(() -> new GlobalException(ResponseInformation.USER_EMAIL_LOGIN_ERROR));
        }
        // 封装用户信息
        return (UserDetails) encapsulateAndSaveUser(user, request);
    }

    /**
     * 封装并保存用户信息
     *
     * @param user    用户
     * @param request 请求
     * @return 用户详情DTO
     */
    private UserDetailDTO encapsulateAndSaveUser(User user, HttpServletRequest request) {
        // 查询用户角色
        List<String> strings = roleMapper.selectRoleLabelByUserId(user.getUserId());
        // 获取设备信息
        String ipAddress = IpUtils.getIpAddr(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        UserAgent userAgent = IpUtils.getUserAgent(request);
        // 保存用户信息
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastIp(ipAddress);
        user.setIpSource(ipSource);
        userMapper.updateById(user);
        return UserDetailDTO.builder()
                .userId(user.getUserId())
                .username(user.getUserName())
                .email(user.getEmail())
                .roles(strings)
                .status(user.isStatus())
                .password(user.getPassWord())
                .nickname(user.getNickName())
                .avatar(user.getAvatar())
                .lastIp(ipAddress)
                .loginType(user.getLoginType())
                .ipSource(ipSource)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now())
                .build();
    }
}
