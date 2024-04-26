package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.UserInfo;
import com.gewuyou.blog.server.mapper.UserInfoMapper;
import com.gewuyou.blog.server.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
