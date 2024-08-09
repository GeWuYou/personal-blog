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
 * 动态安全元数据源类
 * <p>
 * 该类实现了Spring Security的FilterInvocationSecurityMetadataSource接口，
 * 用于根据请求的URL和HTTP方法动态加载安全配置属性。该类通过调用DynamicSecurityService
 * 的接口来加载安全数据源，并在每次请求时动态匹配相应的安全配置。
 * </p>
 *
 * @author gewuyou
 * @since 2024-06-10 下午4:46:40
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * 存储URL和对应的安全配置属性的映射表
     */
    private static Map<String, ConfigAttribute> configAttributeMap = null;

    /**
     * 动态安全服务接口，用于加载安全数据源
     */
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
     * 根据传入的HttpServletRequest对象获取对应的安全配置属性
     * <p>
     * 如果configAttributeMap为空，则重新加载数据源。根据请求的URL和HTTP方法，
     * 使用AntPathMatcher进行模式匹配，返回匹配到的配置属性列表。
     *
     * @param object 要保护的对象，通常为HttpServletRequest
     * @return 返回匹配到的配置属性集合，如果没有匹配到则返回空集合
     * @throws IllegalArgumentException 如果传入的object不是HttpServletRequest实例
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
     * 获取所有定义的安全配置属性
     * <p>
     * 当前实现返回null，因为不需要在启动时对配置属性进行全局验证。
     *
     * @return 返回null
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 判断是否支持传入的类类型
     * <p>
     * 检查是否支持FilterInvocation类或其子类，该方法用于确定该安全元数据源是否
     * 可以为指定类型的对象提供安全配置属性。
     *
     * @param clazz 要查询的类
     * @return 如果支持返回true，否则返回false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
