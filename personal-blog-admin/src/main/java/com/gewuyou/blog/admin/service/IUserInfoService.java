package com.gewuyou.blog.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.UserOnlineDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.UserDisableVO;
import com.gewuyou.blog.common.vo.UserRoleVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息服务接口
 *
 * @author gewuyou
 * @since 2024-07-04 下午9:21:59
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
     * 更新用户角色
     *
     * @param userRoleVO 用户角色VO
     */
    void updateUserRole(UserRoleVO userRoleVO);

    /**
     * 更新用户禁用状态
     *
     * @param userDisableVO 用户禁用VO
     */
    void updateUserDisable(UserDisableVO userDisableVO);

    /**
     * 分页查询在线用户
     *
     * @param conditionVO 条件VO
     * @return 分页结果
     */
    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);

    /**
     * 移除在线用户
     *
     * @param userInfoId 用户ID
     */
    void removeOnlineUser(Long userInfoId);

    /**
     * 更新用户头像
     *
     * @param file 头像文件
     * @return 头像url
     */
    String updateUserAvatar(MultipartFile file);
}
