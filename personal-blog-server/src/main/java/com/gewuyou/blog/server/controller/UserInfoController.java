package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.model.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@RestController
@RequestMapping("/server/user-info")
public class UserInfoController {
    @RequestMapping("/{id}")
    public UserInfo selectUserInfoById(@PathVariable("id") Integer id) {
        // todo 从数据库中查询用户信息
        return null;
    }
}
