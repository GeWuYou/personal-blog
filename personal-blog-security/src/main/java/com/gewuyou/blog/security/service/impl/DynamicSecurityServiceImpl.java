package com.gewuyou.blog.security.service.impl;

import com.gewuyou.blog.common.dto.ResourceRoleDTO;
import com.gewuyou.blog.security.mapper.SecurityRoleMapper;
import com.gewuyou.blog.security.service.interfaces.DynamicSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态安全服务实现
 *
 * @author gewuyou
 * @since 2024-07-16 下午1:34:59
 */
@Configuration
public class DynamicSecurityServiceImpl implements DynamicSecurityService {
    private final SecurityRoleMapper securityRoleMapper;

    @Autowired
    public DynamicSecurityServiceImpl(SecurityRoleMapper securityRoleMapper) {
        this.securityRoleMapper = securityRoleMapper;
    }

    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<ResourceRoleDTO> resourceRoleDTOs = securityRoleMapper.getResourceRoleDTOs();
        for (ResourceRoleDTO resourceRoleDTO : resourceRoleDTOs) {
            StringBuilder sb = new StringBuilder();
            for (String role : resourceRoleDTO.getRoleList()) {
                sb.append(role).append(",");
            }
            // 匿名
            if (resourceRoleDTO.getIsAnonymous().equals(Byte.valueOf("1"))) {
                map.put(resourceRoleDTO.getUrl() + ":" + resourceRoleDTO.getRequestMethod(),
                        new SecurityConfig("anonymous"));
                continue;
            }
            // 如果权限为空，则默认不添加
            if (StringUtils.isNotBlank(sb)) {
                map.put(resourceRoleDTO.getUrl() + ":" + resourceRoleDTO.getRequestMethod(),
                        new SecurityConfig(sb.substring(0, sb.length() - 1)));
            }
        }
        return map;
    }
}
