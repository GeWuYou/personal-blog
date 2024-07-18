package com.gewuyou.blog.admin.strategy.impl;

import com.gewuyou.blog.admin.strategy.interfaces.LoginStrategy;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.vo.LoginVO;
import com.gewuyou.blog.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 用户名或电子邮件密码登录策略
 *
 * @author gewuyou
 * @since 2024-04-25 下午8:13:21
 */
@Service("usernameOrEmailPasswordLoginStrategy")
public class UsernameOREmailPasswordLoginStrategy implements LoginStrategy {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Autowired
    public UsernameOREmailPasswordLoginStrategy(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * 登录方法
     *
     * @param loginData 登录数据
     */
    @Override
    public UserInfoDTO login(Object loginData) {
        LoginVO loginVO = (LoginVO) loginData;
        // 创建一个认证令牌
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginVO.getUsername(),
                loginVO.getPassword()
        );
        // AuthenticationManager校验这个认证信息，返回一个已认证的Authentication
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 获取用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 生成token
        String token = jwtService.generateToken((UserDetailsDTO) userDetails);
        // 将信息类转换为dto
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userDetails;
        // 将dto转换为info
        UserInfoDTO userInfoDTO = BeanCopyUtil.copyObject(userDetailsDTO, UserInfoDTO.class);
        // 设置token
        userInfoDTO.setToken(token);
        return userInfoDTO;
    }
}
