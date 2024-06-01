package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.gewuyou.blog.admin.mapper.RoleMapper;
import com.gewuyou.blog.admin.mapper.UserRoleMapper;
import com.gewuyou.blog.admin.service.IRoleMenuService;
import com.gewuyou.blog.admin.service.IRoleResourceService;
import com.gewuyou.blog.admin.service.IRoleService;
import com.gewuyou.blog.common.constant.CommonConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.RoleDTO;
import com.gewuyou.blog.common.dto.UserRoleDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Role;
import com.gewuyou.blog.common.model.RoleMenu;
import com.gewuyou.blog.common.model.RoleResource;
import com.gewuyou.blog.common.model.UserRole;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    private final IRoleResourceService roleResourceService;

    private final IRoleMenuService roleMenuService;

    private final FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public RoleServiceImpl(IRoleResourceService roleResourceService, IRoleMenuService roleMenuService, FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource, UserRoleMapper userRoleMapper) {
        this.roleResourceService = roleResourceService;
        this.roleMenuService = roleMenuService;
        this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
        this.userRoleMapper = userRoleMapper;
    }


    /**
     * 查询用户角色选项
     *
     * @return 用户角色选项
     */
    @Override
    public List<UserRoleDTO> listUserRoleDTOs() {
        List<Role> roles = baseMapper.selectList(
                new LambdaQueryWrapper<Role>()
                        .select(Role::getId, Role::getRoleName)
        );
        return BeanCopyUtil.copyList(roles, UserRoleDTO.class);
    }

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    @Override
    public PageResultDTO<RoleDTO> listRoleDTOs(ConditionVO conditionVO) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                        Role::getRoleName, conditionVO.getKeywords());
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() ->
                baseMapper.selectCount(queryWrapper)
        );
        Page<RoleDTO> page = new Page<>(conditionVO.getCurrent(), conditionVO.getSize());
        List<RoleDTO> roleDTOs = baseMapper.listRoleDTOs(page, conditionVO);
        try {
            return new PageResultDTO<>(roleDTOs, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
        }
    }

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色信息
     */
    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        Role roleCheck = baseMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(roleCheck) && !(roleCheck.getId().equals(roleVO.getId()))) {
            throw new GlobalException(ResponseInformation.ROLE_EXIST);
        }
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .isDisable(CommonConstant.FALSE)
                .build();
        this.saveOrUpdate(role);
        if (Objects.nonNull(roleVO.getResourceIds())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVO.getId()));
            }
            List<RoleResource> roleResourceList = roleVO.getResourceIds().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        if (Objects.nonNull(roleVO.getMenuIds())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleVO.getId()));
            }
            List<RoleMenu> roleMenuList = roleVO.getMenuIds().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    /**
     * 删除角色
     *
     * @param roleIdList 角色ID列表
     */
    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        Long count = userRoleMapper.selectCount(
                new LambdaQueryWrapper<UserRole>()
                        .in(UserRole::getRoleId, roleIdList)
        );
        if (count > 0) {
            throw new GlobalException(ResponseInformation.ROLE_IN_USE);
        }
        baseMapper.deleteBatchIds(roleIdList);
    }
}
