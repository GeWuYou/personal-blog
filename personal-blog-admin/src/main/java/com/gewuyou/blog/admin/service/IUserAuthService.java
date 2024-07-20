package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.UserAdminDTO;
import com.gewuyou.blog.common.dto.UserAreaDTO;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.LoginVO;
import com.gewuyou.blog.common.vo.QQLoginVO;
import com.gewuyou.blog.common.vo.RegisterVO;

import java.util.List;

/**
 * <p>
 * 用户认证信息表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IUserAuthService extends IService<UserAuth> {

    /**
     * 获取用户区域分布
     *
     * @param conditionVO 条件
     * @return 用户区域分布
     */
    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);


    /**
     * 发送验证码到邮箱
     *
     * @param email      邮件地址
     */
    void sendCodeToEmail(String email);

    /**
     * 验证邮箱并注册
     *
     * @param registerVO 注册数据传输类
     */
    void verifyEmailAndRegister(RegisterVO registerVO);

    /**
     * 验证验证码
     *
     * @param email      邮箱
     * @param verifyCode 验证码
     * @return 是否验证成功
     */
    boolean verifyCode(String email, String verifyCode);

    /**
     * 重置密码
     *
     * @param email    邮箱
     * @param password 密码
     */
    void resetPassword(String email, String password);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean checkUserName(String username);

    /**
     * 获取用户列表
     *
     * @param conditionVO 条件
     * @return 用户列表
     */
    PageResultDTO<UserAdminDTO> listUsers(ConditionVO conditionVO);

    /**
     * 登录
     *
     * @param loginVO 登录信息
     * @return 用户信息
     */
    UserInfoDTO usernameOrEmailPasswordLogin(LoginVO loginVO);


    /**
     * 登出
     *
     * @return 是否登出成功
     */
    ResponseInformation logout();

    /**
     * QQ登录
     *
     * @param qqLoginVO QQ登录信息
     * @return 用户信息
     */
    UserInfoDTO qqLogin(QQLoginVO qqLoginVO);
}
