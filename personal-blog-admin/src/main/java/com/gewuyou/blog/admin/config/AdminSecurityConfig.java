package com.gewuyou.blog.admin.config;

import com.gewuyou.blog.admin.mapper.RoleMapper;
import com.gewuyou.blog.common.dto.ResourceRoleDTO;
import com.gewuyou.blog.security.service.interfaces.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 后台安全配置
 *
 * @author gewuyou
 * @since 2024-06-10 下午8:50:50
 */
@Configuration
public class AdminSecurityConfig {
    private final RoleMapper roleMapper;

    @Autowired
    public AdminSecurityConfig(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<ResourceRoleDTO> resourceRoleDTOs = roleMapper.getResourceRoleDTOs();
            for (ResourceRoleDTO resourceRoleDTO : resourceRoleDTOs) {
                StringBuilder sb = new StringBuilder();
                for (String role : resourceRoleDTO.getRoleList()) {
                    sb.append(role).append(",");
                }
                map.put(resourceRoleDTO.getUrl() + ":" + resourceRoleDTO.getRequestMethod(), new SecurityConfig(sb.substring(0, sb.length() - 1)));
            }
            return map;
        };
    }
}
