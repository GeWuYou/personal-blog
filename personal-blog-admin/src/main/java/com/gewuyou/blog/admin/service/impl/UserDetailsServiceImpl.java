package com.gewuyou.blog.admin.service.impl;

import com.gewuyou.blog.admin.client.ServerClient;
import com.gewuyou.blog.admin.mapper.RoleMapper;
import com.gewuyou.blog.admin.mapper.UserAuthMapper;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.utils.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详情实现类
 *
 * @author gewuyou
 * @since 2024-04-17 下午2:40:16
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthMapper userAuthMapper;

    private final ServerClient serverClient;

    private final HttpServletRequest request;

    private final RoleMapper roleMapper;

    @Autowired
    public UserDetailsServiceImpl(
            UserAuthMapper userAuthMapper,
            ServerClient serverClient,
            RoleMapper roleMapper,
            HttpServletRequest request) {
        this.userAuthMapper = userAuthMapper;
        this.serverClient = serverClient;
        this.roleMapper = roleMapper;
        this.request = request;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        log.info("loadUserByUsername: {}", usernameOrEmail);
        UserAuth userAuth;
        try {
            if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
                throw new GlobalException(ResponseInformation.USERNAME_LOGIN_ERROR);
            }
            EmailValidator validator = EmailValidator.getInstance();

            // 是邮箱还是用户名
            if (validator.isValid(usernameOrEmail)) {
                userAuth = userAuthMapper.selectByEmail(usernameOrEmail)
                        .orElseThrow(() -> new GlobalException(ResponseInformation.USER_EMAIL_LOGIN_ERROR));
            } else {
                userAuth = userAuthMapper.selectByUsername(usernameOrEmail)
                        .orElseThrow(() -> new GlobalException(ResponseInformation.USERNAME_LOGIN_ERROR));
            }
        } catch (GlobalException e) {
            log.error("loadUserByUsername error: {}", e.getErrorMessage());
            throw new GlobalException(e.getResponseInformation());
        }
        // 封装用户信息
        return encapsulateAndSaveUser(userAuth, request);
    }

    /**
     * 封装并保存用户信息
     *
     * @param userAuth    用户认证信息
     * @param request 请求
     * @return 用户详情DTO
     */
    public UserDetailsDTO encapsulateAndSaveUser(UserAuth userAuth, HttpServletRequest request) {
        log.debug("已执行封装并保存用户信息");
        // 查询用户信息
        UserInfo userInfo = serverClient.selectUserInfoById(userAuth.getUserInfoId());
        log.info("userInfo: {}", userInfo);
        // 查询用户角色
        List<String> roles = roleMapper.listRolesByUserInfoId(userInfo.getId());
        // 获取设备信息
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        // 保存用户信息
        userAuth.setLastLoginTime(LocalDateTime.now());
        userAuth.setIpAddress(ipAddress);
        userAuth.setIpSource(ipSource);
        userAuthMapper.updateById(userAuth);
        return UserDetailsDTO.builder()
                .userAuthId(userAuth.getId())
                .userInfoId(userInfo.getId())
                .username(userAuth.getUsername())
                .email(userAuth.getEmail())
                .roles(roles)
                .isDisable(userInfo.getIsDisable())
                .password(userAuth.getPassword())
                .nickname(userInfo.getNickName())
                .avatar(userInfo.getAvatar())
                .lastIp(ipAddress)
                .loginType(userAuth.getLoginType())
                .ipSource(ipSource)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now())
                .build();
    }
}
