package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.UserRoleMapper;
import com.gewuyou.blog.admin.service.IUserRoleService;
import com.gewuyou.blog.common.model.UserRole;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色中间表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
