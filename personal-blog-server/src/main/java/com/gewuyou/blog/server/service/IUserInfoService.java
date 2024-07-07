package com.gewuyou.blog.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.vo.EmailVO;
import com.gewuyou.blog.common.vo.SubscribeVO;
import com.gewuyou.blog.common.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 更新用户头像
     *
     * @param file 头像文件
     * @return 头像url
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 保存用户邮箱
     *
     * @param emailVO 邮箱信息
     */
    void saveUserEmail(EmailVO emailVO);

    /**
     * 更新用户订阅状态
     *
     * @param subscribeVO 订阅信息
     */
    void updateUserSubscribe(SubscribeVO subscribeVO);

    /**
     * 根据用户信息id查询用户信息
     *
     * @param userInfoId 用户信息id
     * @return 用户信息DTO
     */
    UserInfoDTO getUserInfoById(Long userInfoId);
}
