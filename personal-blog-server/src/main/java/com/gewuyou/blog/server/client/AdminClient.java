package com.gewuyou.blog.server.client;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 管理客户端
 *
 * @author gewuyou
 * @since 2024-05-20 下午6:29:53
 */
@FeignClient(name = "personal-blog-admin", url = "http://localhost:8080" + InterfacePermissionConstant.ADMIN_BASE_URL)
public interface AdminClient {
    @GetMapping("/website/config")
    Result<WebsiteConfigDTO> getWebsiteConfig();
}
