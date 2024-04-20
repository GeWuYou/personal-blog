package com.gewuyou.blog.admin.service;

import com.gewuyou.blog.common.dto.RegisterDTO;

/**
 * 管理服务接口
 *
 * @author gewuyou
 * @since 2024-04-19 下午8:02:33
 */
public interface IAdminService {

    /**
     * 发送注册邮件
     *
     * @param email      邮件地址
     * @param sessionId  会话ID
     * @param isRegister (邮箱)是否注册
     * @return 是否发送成功
     */
    boolean sendEmail(String email, String sessionId, boolean isRegister);

    /**
     * 验证邮箱并注册
     *
     * @param registerDTO 注册数据传输类
     * @param sessionId   会话ID
     * @return 是否注册成功
     */
    boolean verifyEmailAndRegister(RegisterDTO registerDTO, String sessionId);

    /**
     * 验证验证码
     *
     * @param email      邮箱
     * @param verifyCode 验证码
     * @param sessionId  会话ID
     * @param isRegister (邮箱)是否注册
     * @return 是否验证成功
     */
    boolean verifyCode(String email, String verifyCode, String sessionId, boolean isRegister);

    /**
     * 重置密码
     *
     * @param email    邮箱
     * @param password 密码
     * @return 是否重置成功
     */
    boolean resetPassword(String email, String password);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean checkUserName(String username);
}
