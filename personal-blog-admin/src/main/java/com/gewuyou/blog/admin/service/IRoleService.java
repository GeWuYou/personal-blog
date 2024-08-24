package com.gewuyou.blog.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.RoleDTO;
import com.gewuyou.blog.common.dto.UserRoleDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.Role;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.RoleVO;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询用户角色选项
     *
     * @return 用户角色选项
     */
    List<UserRoleDTO> listUserRoleDTOs();

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    PageResult<RoleDTO> listRoleDTOs(ConditionVO conditionVO);

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色信息
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 删除角色
     *
     * @param roleIdList 角色ID列表
     */
    void deleteRoles(List<Integer> roleIdList);
}
