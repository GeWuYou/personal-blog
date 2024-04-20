package com.gewuyou.blog.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.config.entity.SecurityIgnoreUrl;
import com.gewuyou.blog.admin.filter.ExceptionHandlerFilter;
import com.gewuyou.blog.admin.filter.JwtAuthorizationFilter;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.ResponseInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 安全配置
 *
 * @author gewuyou
 * @since 2024-04-16 下午7:02:59
 */

@Slf4j
@Configuration
public class SecurityConfig {

    private final ObjectMapper json;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final SecurityIgnoreUrl securityIgnoreUrl;


    @Autowired
    public SecurityConfig(
            ObjectMapper json,
            JwtAuthorizationFilter jwtAuthorizationFilter,
            ExceptionHandlerFilter exceptionHandlerFilter,
            SecurityIgnoreUrl securityIgnoreUrl
    ) {
        this.json = json;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
        this.securityIgnoreUrl = securityIgnoreUrl;
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        for (String url : securityIgnoreUrl.getUrls()) {
            log.debug("url:{}", url);
        }
        return http
                // 关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 添加CORS 相关的配置
                .cors(
                        cors -> cors
                                .configurationSource(createCorsConfiguration()))
                // 添加没有权限时进行其他操作时返回的响应
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(this::onAuthenticationFailure))
                .authorizeHttpRequests(
                        authorization -> authorization
                                // 放行所有请求
                                // .anyRequest()
                                // 放行登录接口
                                .requestMatchers(securityIgnoreUrl.getUrls()).permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .logout(
                        logout -> logout
                                // 设置退出接口
                                .logoutUrl(InterfacePermissionConstant.BASE_URL + "/admin/logout")
                                .logoutSuccessHandler(this::onAuthenticationSuccess)
                                // 默认会话失效
                                .clearAuthentication(true)
                                // 清除认证标记
                                .invalidateHttpSession(true)
                )
                .httpBasic(withDefaults())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                // 在LogoutFilter之前先进行异常捕获
                .addFilterBefore(exceptionHandlerFilter, LogoutFilter.class).build();
    }


    /**
     * 没有权限时进行其他操作时返回的响应
     *
     * @param request   请求
     * @param response  响应
     * @param exception 异常
     * @apiNote
     * @since 2023/6/11 0:52
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json.writeValueAsString(Result.failure(ResponseInformation.INSUFFICIENT_PERMISSIONS)));
    }

    /**
     * 注销成功后的响应方法
     *
     * @param request  请求
     * @param response 响应
     * @apiNote
     * @since 2023/6/11 0:49
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json.writeValueAsString(Result.success(ResponseInformation.LOGOUT_SUCCESS)));
    }

    /**
     * 添加CORS 相关的配置
     *
     * @return org.springframework.web.cors.CorsConfigurationSource
     * @apiNote
     * @since 2023/6/19 18:08
     */
    private CorsConfigurationSource createCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许http://localhost:8080跨域请求
        corsConfiguration.addAllowedOriginPattern("http://localhost:8080");
        corsConfiguration.addAllowedOriginPattern("http://localhost:5174");
        // 设置携带cookie
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
