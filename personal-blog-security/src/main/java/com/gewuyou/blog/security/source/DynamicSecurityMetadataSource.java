package com.gewuyou.blog.security.source;

import com.gewuyou.blog.security.service.interfaces.DynamicSecurityService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 动态安全元数据源
 *
 * @author gewuyou
 * @since 2024-06-10 下午4:46:40
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttributeMap = null;

    private final DynamicSecurityService dynamicSecurityService;


    @PostConstruct
    public void loadDataSources() {
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }


    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    public DynamicSecurityMetadataSource(DynamicSecurityService dynamicSecurityService) {
        this.dynamicSecurityService = dynamicSecurityService;
    }

    /**
     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
     *
     * @param object the object being secured
     * @return the attributes that apply to the passed in secured object. Should return an
     * empty collection if there are no applicable attributes.
     * @throws IllegalArgumentException if the passed object is not of a type supported by
     *                                  the <code>SecurityMetadataSource</code> implementation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (Objects.isNull(configAttributeMap)) {
            this.loadDataSources();
        }
        if (!(object instanceof HttpServletRequest request)) {
            throw new IllegalArgumentException("Object must be an instance of " +
                    "HttpServletRequest");
        }
        String method = request.getMethod();
        String url = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (Map.Entry<String, ConfigAttribute> entry : configAttributeMap.entrySet()) {
            String[] key = entry.getKey().split(":");
            if (antPathMatcher.match(key[0], url) && method.equals(key[1])) {
                String[] roles = entry.getValue().getAttribute().split(",");
                return SecurityConfig.createList(roles);
            }
        }
        // 没有匹配到，返回空集合
        return SecurityConfig.createList();
    }

    /**
     * If available, returns all of the {@code ConfigAttribute}s defined by the
     * implementing class.
     * <p>
     * This is used by the {@link} to perform startup time
     * validation of each {@code ConfigAttribute} configured against it.
     *
     * @return the {@code ConfigAttribute}s or {@code null} if unsupported
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
     * provide {@code ConfigAttribute}s for the indicated secure object type.
     *
     * @param clazz the class that is being queried
     * @return true if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
