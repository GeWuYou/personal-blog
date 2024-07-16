package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.security.source.DynamicSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态权限元数据控制器
 *
 * @author gewuyou
 * @since 2024-07-16 下午6:58:07
 */
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/dynamic-security-metadata-source")
@Slf4j
public class DynamicSecurityMetadataSourceController {
    private final DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    public DynamicSecurityMetadataSourceController(DynamicSecurityMetadataSource dynamicSecurityMetadataSource) {
        this.dynamicSecurityMetadataSource = dynamicSecurityMetadataSource;
    }

    @DeleteMapping("/clear")
    public void clearConfigAttributeMap() {
        dynamicSecurityMetadataSource.clearDataSource();
    }
}
