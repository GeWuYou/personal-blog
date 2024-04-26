package com.gewuyou.blog.admin.strategy.interfaces;

import com.gewuyou.blog.common.dto.UserInfoDTO;

/**
 * 登录策略
 *
 * @author gewuyou
 * @since 2024-04-25 下午5:56:58
 */
public interface LoginStrategy {
    /**
     * 登录方法
     *
     * @param loginData 登录数据
     */
    UserInfoDTO login(Object loginData);
}
