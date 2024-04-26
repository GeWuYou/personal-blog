package com.gewuyou.blog.admin.client;

import com.gewuyou.blog.common.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 服务器客户端
 *
 * @author gewuyou
 * @since 2024-04-23 下午9:55:33
 */
@FeignClient("personal-blog-server")
public interface ServerClient {
    @PostMapping("/server/user-info/insert")
    void userInfoInsert(UserInfo userInfo);

    @GetMapping("/server/user-info/{id}")
    UserInfo selectUserInfoById(@PathVariable(name = "id") Long id);

}
