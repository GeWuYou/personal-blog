package com.gewuyou.blog.admin.manager;

import com.gewuyou.blog.admin.handler.FilterInvocationSecurityMetadataSourceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 授权管理器
 *
 * @author gewuyou
 * @since 2024-06-01 上午12:31:39
 */
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Autowired
    public CustomAuthorizationManager(FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource) {
        this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
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
        // 获取当前请求
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        // 获取当前用户的权限列表
        List<String> permissionList = authentication
                .get()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        // 获取当前请求的权限列表
        Collection<ConfigAttribute> attributes = filterInvocationSecurityMetadataSource.getAttributes(request);
        // 判断当前用户是否有当前请求的权限
        for (ConfigAttribute attribute : attributes) {
            if (permissionList.contains(attribute.getAttribute())) {
                return new AuthorizationDecision(true);
            }
        }
        // 没有权限
        return new AuthorizationDecision(false);
    }
}
