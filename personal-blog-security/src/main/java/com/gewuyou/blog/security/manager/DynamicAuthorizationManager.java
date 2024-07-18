package com.gewuyou.blog.security.manager;

import com.gewuyou.blog.security.source.DynamicSecurityMetadataSource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * 动态授权管理器
 *
 * @author gewuyou
 * @since 2024-06-10 下午5:33:20
 */
@Slf4j
@Component
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final DynamicSecurityMetadataSource dynamicSecurityMetadataSource;


    @Autowired
    public DynamicAuthorizationManager(
            DynamicSecurityMetadataSource dynamicSecurityMetadataSource) {
        this.dynamicSecurityMetadataSource = dynamicSecurityMetadataSource;
    }

    /**
     * Determines if access is granted for a specific authentication and requestAuthorizationContext.
     *
     * @param authentication              the {@link Supplier} of the {@link Authentication} to check
     * @param requestAuthorizationContext the {@link RequestAuthorizationContext} requestAuthorizationContext to check
     * @return an {@link AuthorizationDecision} or null if no decision could be made
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        // 判断是否是options请求
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return new AuthorizationDecision(true);
        }
        log.info("检查的URL：{}", request.getRequestURI());
        // 获取当前用户的权限列表
        List<String> permissions = authentication
                .get()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        log.info("当前用户的权限列表：{}", permissions);
        // 获取当前请求的权限列表
        Collection<ConfigAttribute> attributes = dynamicSecurityMetadataSource.getAttributes(request);
        // 遍历权限列表，判断是否有访问权限
        // 判断当前用户是否有当前请求的权限
        for (ConfigAttribute attribute : attributes) {
            log.info("当前请求的权限：{}, 请求的URL：{}", attribute.getAttribute(), request.getRequestURI());
            // 允许匿名访问
            if ("anonymous".equals(attribute.getAttribute())) {
                log.info("允许匿名访问：{}", request.getRequestURI());
                return new AuthorizationDecision(true);
            }
            if (permissions.contains(attribute.getAttribute())) {
                log.info("当前用户有访问权限：{}", request.getRequestURI());
                return new AuthorizationDecision(true);
            }
        }
        log.info("当前用户没有访问权限：{}", request.getRequestURI());
        // 没有权限
        return new AuthorizationDecision(false);
    }
}
