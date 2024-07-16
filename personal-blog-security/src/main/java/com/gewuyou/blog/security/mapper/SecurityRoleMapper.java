package com.gewuyou.blog.security.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.dto.ResourceRoleDTO;
import com.gewuyou.blog.common.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色表 Mapper 接口
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface SecurityRoleMapper extends BaseMapper<Role> {
    /**
     * 查询资源角色列表
     *
     * @return 资源角色列表
     */
    List<ResourceRoleDTO> getResourceRoleDTOs();
}
