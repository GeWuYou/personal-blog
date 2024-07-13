package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.MenuMapper;
import com.gewuyou.blog.admin.mapper.RoleMenuMapper;
import com.gewuyou.blog.admin.service.IMenuService;
import com.gewuyou.blog.common.constant.CommonConstant;
import com.gewuyou.blog.common.dto.LabelOptionDTO;
import com.gewuyou.blog.common.dto.MenuDTO;
import com.gewuyou.blog.common.dto.UserMenuDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Menu;
import com.gewuyou.blog.common.model.RoleMenu;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.CollectionUtil;
import com.gewuyou.blog.common.utils.DateUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.IsHiddenVO;
import com.gewuyou.blog.common.vo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.CommonConstant.COMPONENT;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(
            RoleMenuMapper roleMenuMapper
    ) {
        this.roleMenuMapper = roleMenuMapper;
    }

    /**
     * 获取用户菜单列表
     *
     * @return 用户菜单列表
     */
    @Override
    public List<UserMenuDTO> listUserMenus() {
        // 获取用户菜单列表
        List<Menu> menus = baseMapper.listMenusByUserInfoId(UserUtil.getUserDetailsDTO().getUserInfoId());
        // 筛选出顶级菜单
        var topMenuDTOs = listTopMenuDTOs(menus);
        // 筛选出分组后的二级菜单
        var menuMapDTOs = listMenuDTOChildrenMap(menus);
        // 将顶级菜单列表转换为 UserMenuDTO 对象列表
        return convertUserMenuList(topMenuDTOs, menuMapDTOs);
    }

    /**
     * 获取菜单列表
     *
     * @param conditionVO 条件
     * @return 菜单列表
     */
    @Override
    public List<MenuDTO> listMenus(ConditionVO conditionVO) {
        // 根据搜索内容进行模糊查询获取符合条件的菜单列表
        List<Menu> menus = baseMapper
                .selectList(new LambdaQueryWrapper<Menu>()
                        .like(
                                StringUtils
                                        .isNotBlank(conditionVO.getKeywords()),
                                Menu::getName,
                                conditionVO.getKeywords()));
        // 筛选出顶级菜单
        var topMenus = listTopMenuDTOs(menus);
        // 筛选出分组后的二级菜单并与关联顶级菜单id
        var childrenMap = listMenuDTOChildrenMap(menus);
        // 将子菜单绑定到父菜单下
        var menuDTOs = CollectionUtil.processItemsWithChildren(topMenus, childrenMap, MenuDTO::getId, MenuDTO::setChildren, true);
        // 在childrenMap中剩余菜单是没有顶级菜单的，需要单独添加到菜单列表中
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            menuDTOs
                    .addAll(
                            childrenMap
                                    .values()
                                    .stream()
                                    .flatMap(Collection::stream)
                                    .toList()
                    );
        }
        // 返回菜单列表
        return menuDTOs;
    }

    /**
     * 保存或更新菜单
     *
     * @param menuVO 菜单信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuVO menuVO) {
        Menu menu = BeanCopyUtil.copyObject(menuVO, Menu.class);
        this.saveOrUpdate(menu);
    }

    /**
     * 更新菜单是否隐藏
     *
     * @param isHiddenVO 菜单是否隐藏信息
     */
    @Override
    public void updateMenuIsHidden(IsHiddenVO isHiddenVO) {
        Menu menu = BeanCopyUtil.copyObject(isHiddenVO, Menu.class);
        this.updateById(menu);
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    @Override
    public void deleteMenu(Integer menuId) {
        // 查询菜单是否存在角色关联
        Long count = roleMenuMapper.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        // 如果存在角色关联，则不能删除
        if (count > 0) {
            throw new GlobalException(ResponseInformation.MENU_HAS_BEEN_ASSOCIATED_WITH_A_ROLE);
        }
        // 查找该菜单是否存在子菜单
        List<Integer> menuIds = new ArrayList<>(baseMapper.selectList(new LambdaQueryWrapper<Menu>()
                        .select(Menu::getId)
                        .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getId)
                .toList());
        menuIds.add(menuId);
        // 删除菜单及其子菜单
        baseMapper.deleteBatchIds(menuIds);
    }

    /**
     * 获取角色菜单选项列表
     *
     * @return 角色菜单选项列表
     */
    @Override
    public List<LabelOptionDTO> listRoleMenuOptions() {
        // 获取菜单列表信息
        List<Menu> menus = baseMapper.selectList(
                new LambdaQueryWrapper<Menu>()
                        .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum)
        );
        // 筛选出顶级菜单
        var topMenus = listTopMenuDTOs(menus);
        // 筛选出分组后的二级菜单并与关联顶级菜单id
        var childrenMap = listMenuDTOChildrenMap(menus);
        return topMenus
                .stream()
                .map(
                        item -> {
                            List<LabelOptionDTO> list = new ArrayList<>();
                            var children = childrenMap.get(item.getId());
                            // 判断当前需要转换的顶级菜单是否有子菜单
                            if (CollectionUtils.isNotEmpty(children)) {
                                list = children
                                        .stream()
                                        .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                                        .map(
                                                menu -> LabelOptionDTO.builder()
                                                        .id(menu.getId())
                                                        .label(menu.getName())
                                                        .build())
                                        .toList();
                            }
                            return LabelOptionDTO.builder()
                                    .id(item.getId())
                                    .label(item.getName())
                                    .children(list)
                                    .build();
                        }).toList();
    }

    /**
     * 将顶级菜单列表和菜单组映射转换为 UserMenuDTO 对象列表。
     *
     * @param TopMenuDTOs 要转换的顶级菜单列表
     * @param menuDTOMap  菜单组映射
     * @return 转换后的 UserMenuDTO 对象的列表
     */
    private List<UserMenuDTO> convertUserMenuList(List<MenuDTO> TopMenuDTOs, Map<Integer, List<MenuDTO>> menuDTOMap) {
        return TopMenuDTOs
                .stream()
                .map(
                        item -> {
                            UserMenuDTO userMenuDTO = new UserMenuDTO();
                            List<UserMenuDTO> childrenUserMenuDTO = new ArrayList<>();
                            var childrenMenu = menuDTOMap.get(item.getId());
                            // 判断当前需要转换的顶级菜单是否有子菜单
                            if (CollectionUtils.isEmpty(childrenMenu)) {
                                userMenuDTO.setPath(item.getPath());
                                userMenuDTO.setComponent(COMPONENT);
                                childrenUserMenuDTO.add(
                                        UserMenuDTO
                                                .builder()
                                                .path("")
                                                .name(item.getName())
                                                .icon(item.getIcon())
                                                .component(item.getComponent())
                                                .build()
                                );
                            } else {
                                userMenuDTO = BeanCopyUtil.copyObject(item, UserMenuDTO.class);
                                childrenUserMenuDTO = childrenMenu
                                        .stream()
                                        .sorted(Comparator.comparingInt(MenuDTO::getOrderNum))
                                        .map(menu -> {
                                            UserMenuDTO dto = BeanCopyUtil.copyObject(menu, UserMenuDTO.class);
                                            dto.setHidden(menu.getIsHidden().equals(CommonConstant.TRUE));
                                            return dto;
                                        })
                                        .collect(Collectors.toList());
                            }
                            // 设置隐藏属性
                            userMenuDTO.setHidden(item.getIsHidden().equals(CommonConstant.TRUE));
                            // 设置子菜单
                            userMenuDTO.setChildren(childrenUserMenuDTO);
                            return userMenuDTO;
                        }
                ).collect(Collectors.toList());
    }

    /**
     * 返回一个映射，其中键是给定菜单列表的父 ID，值是具有该父 ID 的菜单列表。
     *
     * @param menus 需要按父 ID 分组的菜单列表
     * @return 一个映射，其中键是父 ID，值是具有该父 ID 的菜单列表
     */
    private Map<Integer, List<MenuDTO>> listMenuDTOChildrenMap(List<Menu> menus) {
        return menus
                .stream()
                // 过滤出二级菜单
                .filter(item -> Objects.nonNull(item.getParentId()))
                // 按父菜单ID分组
                .collect(Collectors.groupingBy(Menu::getParentId,
                        Collectors.mapping(item -> {
                            var menuDTO = BeanCopyUtil.copyObject(item, MenuDTO.class);
                            var createTime = item.getCreateTime();
                            // 转换时间格式
                            if (Objects.nonNull(createTime)) {
                                menuDTO.setCreateTime(DateUtil.convertToDate(createTime));
                            }
                            return menuDTO;
                        }, Collectors.toList())));
    }

    /**
     * 从给定的菜单列表中筛选出顶级菜单，按排序号对它们进行排序，然后返回排序列表。
     *
     * @param menus 要筛选和排序的菜单列表
     * @return 顶级菜单的排序列表
     */
    private List<MenuDTO> listTopMenuDTOs(List<Menu> menus) {
        return menus
                .stream()
                // 过滤出一级菜单
                .filter(item -> Objects.isNull(item.getParentId()))

                // 排序
                .sorted(Comparator.comparingInt(Menu::getOrderNum))
                .map(item -> {
                    var menuDTO = BeanCopyUtil.copyObject(item, MenuDTO.class);
                    var createTime = item.getCreateTime();
                    // 转换时间格式
                    if (Objects.nonNull(createTime)) {
                        menuDTO.setCreateTime(DateUtil.convertToDate(createTime));
                    }
                    return menuDTO;
                })
                // 转为重新返回排序后的一级菜单
                .collect(Collectors.toList());
    }
}
