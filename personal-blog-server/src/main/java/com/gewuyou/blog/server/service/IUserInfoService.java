package com.gewuyou.blog.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.model.UserInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo selectUserInfoById(Long id);

    /**
     * 插入用户信息
     *
     * @param userInfo 用户信息
     */
    void insert(UserInfo userInfo);

    /**
     * 获取用户数量
     *
     * @return 用户数量
     */
    Long selectCount();
}
