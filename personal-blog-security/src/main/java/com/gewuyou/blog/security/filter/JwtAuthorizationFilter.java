package com.gewuyou.blog.security.filter;


import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.security.config.SecurityIgnoreUrl;
import com.gewuyou.blog.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Jwt 授权过滤器
 *
 * @author gewuyou
 * @since 2024-04-17 下午5:20:46
 */
@Component
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    private final SecurityIgnoreUrl securityIgnoreUrl;

    private final JwtService jwtService;

    @Autowired
    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            SecurityIgnoreUrl securityIgnoreUrl,
            JwtService jwtService
    ) {
        super(authenticationManager);
        this.securityIgnoreUrl = securityIgnoreUrl;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Stream<AntPathRequestMatcher> matchers = Arrays.stream(securityIgnoreUrl.getUrls()).map(AntPathRequestMatcher::new);
        // 如果匹配到需要放行的路径，则直接放行
        if (matchers.anyMatch(matcher -> matcher.matches(request))) {
            log.info("已放行路径：{}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        log.info("验证路径：{}", request.getRequestURI());
        // 从header中获取token
        String token = request.getHeader(header);
        if (token == null || !token.startsWith(prefix)) {
            log.error("token为空或格式错误：{}", token);
            throw new GlobalException(ResponseInformation.INVALID_TOKEN_REQUEST);
        }
        token = token.substring(prefix.length() + 1);
        // 如果校验失败说明登录已过期
        try {
            // 校验tokens是否有效
            if (!jwtService.validateToken(token)) {
                log.error("token校验失败：{}", token);
                throw new GlobalException(ResponseInformation.INVALID_TOKEN);
            }
            // 校验token是否过期
            if (jwtService.isTokenExpired(token)) {
                log.error("token已过期：{}", token);
                throw new GlobalException(ResponseInformation.TOKEN_EXPIRED);
            }
        } catch (GlobalException e) {
            throw new GlobalException(ResponseInformation.LOGIN_EXPIRED);
        }
        //  从token中获取当前登录用户UserDetails并强转为UserDetailsDto
        var userDetailsDTO = jwtService.getUserDetailsDTOFromToken(token);
        // 如果获取不到用户信息，则说明登录过期
        if (Objects.isNull(userDetailsDTO)) {
            throw new GlobalException(ResponseInformation.LOGIN_EXPIRED);
        }
        // 刷新token
        jwtService.refreshToken(userDetailsDTO);
        // 构建认证token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetailsDTO, null, userDetailsDTO.getAuthorities());
        // 设置当前登录用户
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
