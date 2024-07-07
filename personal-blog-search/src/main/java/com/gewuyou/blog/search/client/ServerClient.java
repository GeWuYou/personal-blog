package com.gewuyou.blog.search.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 服务器客户端
 *
 * @author gewuyou
 * @since 2024-05-15 下午3:24:03
 */
@FeignClient(name = "personal-blog-server", url = "http://localhost:8084")
public interface ServerClient {
}
