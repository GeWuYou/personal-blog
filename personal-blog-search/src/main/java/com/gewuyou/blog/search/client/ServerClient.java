package com.gewuyou.blog.search.client;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 服务器客户端
 *
 * @author gewuyou
 * @since 2024-05-15 下午3:24:03
 */
@FeignClient(name = "personal-blog-server", url = "http://localhost:8084" + InterfacePermissionConstant.SERVER_BASE_URL)
public interface ServerClient {
}
