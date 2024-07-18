package com.gewuyou.blog.admin.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gewuyou.blog.admin.mapper.UserAuthMapper;
import com.gewuyou.blog.admin.mapper.UserInfoMapper;
import com.gewuyou.blog.admin.mapper.UserRoleMapper;
import com.gewuyou.blog.admin.service.impl.UserDetailsServiceImpl;
import com.gewuyou.blog.admin.strategy.interfaces.LoginStrategy;
import com.gewuyou.blog.common.constant.CommonConstant;
import com.gewuyou.blog.common.dto.SocialTokenDTO;
import com.gewuyou.blog.common.dto.SocialUserInfoDTO;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.enums.RoleEnum;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.model.UserRole;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.IpUtil;
import com.gewuyou.blog.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 抽象社交登录策略实现
 *
 * @author gewuyou
 * @since 2024-07-18 上午10:12:30
 */
@Service
public abstract class AbstractSocialLoginStrategyImpl implements LoginStrategy {
    private final UserAuthMapper userAuthMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final HttpServletRequest request;
    private final UserInfoMapper userInfoMapper;

    public AbstractSocialLoginStrategyImpl(UserAuthMapper userAuthMapper, UserRoleMapper userRoleMapper, UserDetailsServiceImpl userDetailsService, JwtService jwtService, HttpServletRequest request, UserInfoMapper userInfoMapper) {
        this.userAuthMapper = userAuthMapper;
        this.userRoleMapper = userRoleMapper;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.request = request;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * 登录方法
     *
     * @param loginData 登录数据
     */
    @Override
    public UserInfoDTO login(Object loginData) {
        UserDetailsDTO userDetailsDTO;
        SocialTokenDTO socialToken = getSocialToken(loginData);
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAuth user = getUserAuth(socialToken);
        if (Objects.nonNull(user)) {
            userDetailsDTO = getUserDetail(user, ipAddress, ipSource);
        } else {
            userDetailsDTO = saveUserDetail(socialToken, ipAddress, ipSource);
        }
        if (userDetailsDTO.getIsDisable().equals(CommonConstant.TRUE)) {
            throw new GlobalException(ResponseInformation.USER__ACCOUNT_HAS_BEEN_LOCKED);
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetailsDTO, null, userDetailsDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserInfoDTO userInfoDTO = BeanCopyUtil.copyObject(userDetailsDTO, UserInfoDTO.class);
        String token = jwtService.generateToken(userDetailsDTO);
        userInfoDTO.setToken(token);
        return userInfoDTO;
    }

    public abstract SocialTokenDTO getSocialToken(Object data);

    public abstract SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO);

    private UserAuth getUserAuth(SocialTokenDTO socialTokenDTO) {
        return userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, socialTokenDTO.getOpenId())
                .eq(UserAuth::getLoginType, socialTokenDTO.getLoginType()));
    }

    private UserDetailsDTO getUserDetail(UserAuth user, String ipAddress, String ipSource) {
        userAuthMapper.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getLastLoginTime, LocalDateTime.now())
                .set(UserAuth::getIpAddress, ipAddress)
                .set(UserAuth::getIpSource, ipSource)
                .eq(UserAuth::getId, user.getId()));
        return userDetailsService.encapsulateAndSaveUser(user, request);
    }

    private UserDetailsDTO saveUserDetail(SocialTokenDTO socialToken, String ipAddress, String ipSource) {
        SocialUserInfoDTO socialUserInfo = getSocialUserInfo(socialToken);
        UserInfo userInfo = UserInfo.builder()
                .nickName(socialUserInfo.getNickname())
                .avatar(socialUserInfo.getAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(socialToken.getOpenId())
                .password(socialToken.getAccessToken())
                .loginType(socialToken.getLoginType())
                .lastLoginTime(LocalDateTime.now())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .build();
        userAuthMapper.insert(userAuth);
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        return userDetailsService.encapsulateAndSaveUser(userAuth, request);
    }
}
