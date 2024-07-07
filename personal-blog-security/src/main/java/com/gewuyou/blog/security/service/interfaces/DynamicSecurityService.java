package com.gewuyou.blog.security.service.interfaces;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态安全服务
 *
 * @author gewuyou
 * @since 2024-06-10 下午4:34:49
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
