package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.server.mapper.UserInfoMapper;
import com.gewuyou.blog.server.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {


    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserInfo selectUserInfoById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 插入用户信息
     *
     * @param userInfo 用户信息
     */
    @Override
    public void insert(UserInfo userInfo) {
        baseMapper.insert(userInfo);
    }

    /**
     * 获取用户数量
     *
     * @return 用户数量
     */
    @Override
    public Long selectCount() {
        return baseMapper.selectCount(null);
    }
}
