package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.ResourceRoleDTO;
import com.gewuyou.blog.common.dto.RoleDTO;
import com.gewuyou.blog.common.model.Role;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户ID查询角色标签
     *
     * @param userInfo 用户ID
     * @return 角色标签列表
     */
    List<String> listRolesByUserInfoId(@Param("userInfoId") Long userInfo);

    /**
     * 查询角色列表
     *
     * @param page        分页对象
     * @param conditionVO 条件对象
     * @return 角色列表
     */
    List<RoleDTO> listRoleDTOs(Page<RoleDTO> page, @Param("conditionVO") ConditionVO conditionVO);

    /**
     * 查询资源角色列表
     *
     * @return 资源角色列表
     */
    List<ResourceRoleDTO> getResourceRoleDTOs();
}
