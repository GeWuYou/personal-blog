package com.gewuyou.blog.admin.service.impl;


import com.gewuyou.blog.admin.mapper.RoleMapper;
import com.gewuyou.blog.admin.service.IRoleService;
import com.gewuyou.blog.common.model.Role;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
