package com.gewuyou.blog.admin.strategy.impl;

import com.gewuyou.blog.admin.security.service.JwtService;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.enums.TokenType;
import com.gewuyou.blog.common.utils.BeanCopyUtils;
import com.gewuyou.blog.common.vo.LoginVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 用户名或电子邮件密码登录策略
 *
 * @author gewuyou
 * @since 2024-04-25 下午8:13:21
 */
@Service("usernameOREmailPasswordLoginStrategy")
public class UsernameOREmailPasswordLoginStrategy extends AbstractOrdinaryLoginStrategy {

    private final AuthenticationManager authenticationManager;

    private final HttpServletResponse response;

    private final JwtService jwtService;

    @Autowired
    public UsernameOREmailPasswordLoginStrategy(
            AuthenticationManager authenticationManager,
            HttpServletResponse response,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.response = response;
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
        // 获取用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 生成刷新token和访问token
        String refreshToken = jwtService.generateToken(userDetails, TokenType.RefreshToken);
        String accessToken = jwtService.generateToken(userDetails, TokenType.AccessToken);
        // 设置cookie
        response.addHeader("Set-Cookie", "refreshToken=" + refreshToken + ";path=/;HttpOnly");
        response.addHeader("Set-Cookie", "accessToken=" + accessToken + ";path=/;HttpOnly");
        // 将信息类转换为dto
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userDetails;
        return BeanCopyUtils.copyObject(userDetailsDTO, UserInfoDTO.class);
    }
}
