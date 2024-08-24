package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.client.ServerClient;
import com.gewuyou.blog.admin.mapper.RoleMapper;
import com.gewuyou.blog.admin.mapper.RoleMenuMapper;
import com.gewuyou.blog.admin.mapper.RoleResourceMapper;
import com.gewuyou.blog.admin.mapper.UserRoleMapper;
import com.gewuyou.blog.admin.service.IRoleMenuService;
import com.gewuyou.blog.admin.service.IRoleResourceService;
import com.gewuyou.blog.admin.service.IRoleService;
import com.gewuyou.blog.common.constant.CommonConstant;
import com.gewuyou.blog.common.dto.RoleDTO;
import com.gewuyou.blog.common.dto.UserRoleDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Role;
import com.gewuyou.blog.common.model.RoleMenu;
import com.gewuyou.blog.common.model.RoleResource;
import com.gewuyou.blog.common.model.UserRole;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.RoleVO;
import com.gewuyou.blog.security.source.DynamicSecurityMetadataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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

    private final DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    private final UserRoleMapper userRoleMapper;
    private final RoleResourceMapper roleResourceMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final ServerClient serverClient;
    private final Executor asyncTaskExecutor;

    @Autowired
    public RoleServiceImpl(IRoleResourceService roleResourceService,
                           IRoleMenuService roleMenuService,
                           DynamicSecurityMetadataSource dynamicSecurityMetadataSource,
                           UserRoleMapper userRoleMapper, RoleResourceMapper roleResourceMapper, RoleMenuMapper roleMenuMapper, ServerClient serverClient, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.roleResourceService = roleResourceService;
        this.roleMenuService = roleMenuService;
        this.dynamicSecurityMetadataSource = dynamicSecurityMetadataSource;
        this.userRoleMapper = userRoleMapper;
        this.roleResourceMapper = roleResourceMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.serverClient = serverClient;
        this.asyncTaskExecutor = asyncTaskExecutor;
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
    public PageResult<RoleDTO> listRoleDTOs(ConditionVO conditionVO) {
        return CompletableFuture.supplyAsync(() ->
                        baseMapper.selectCount(new LambdaQueryWrapper<Role>()
                                .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                                        Role::getRoleName, conditionVO.getKeywords()))
                ).thenCombine(CompletableFuture.supplyAsync(
                                () -> baseMapper.listRoleDTOs(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO)),
                        (count, roleDTOs) -> new PageResult<>(roleDTOs, count))
                .exceptionally(e -> {
                    log.error("获取角色列表失败!", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色信息
     */
    @Override
    @Async("asyncTaskExecutor")
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
            dynamicSecurityMetadataSource.clearDataSource();
            serverClient.clearConfigAttributeMap();
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
    @Async("asyncTaskExecutor")
    public void deleteRoles(List<Integer> roleIdList) {
        Long count = userRoleMapper.selectCount(
                new LambdaQueryWrapper<UserRole>()
                        .in(UserRole::getRoleId, roleIdList)
        );
        if (count > 0) {
            throw new GlobalException(ResponseInformation.ROLE_IN_USE_BY_USER);
        }
        count = roleResourceMapper.selectCount(
                new LambdaQueryWrapper<RoleResource>()
                        .in(RoleResource::getRoleId, roleIdList)
        );
        if (count > 0) {
            throw new GlobalException(ResponseInformation.ROLE_IN_USE_BY_RESOURCE);
        }
        count = roleMenuMapper.selectCount(
                new LambdaQueryWrapper<RoleMenu>()
                        .in(RoleMenu::getRoleId, roleIdList)
        );
        if (count > 0) {
            throw new GlobalException(ResponseInformation.ROLE_IN_USE_BY_MENU);
        }
        baseMapper.deleteBatchIds(roleIdList);
    }
}
