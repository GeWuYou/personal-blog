package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.LabelOptionDTO;
import com.gewuyou.blog.common.dto.MenuDTO;
import com.gewuyou.blog.common.dto.UserMenuDTO;
import com.gewuyou.blog.common.model.Menu;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.IsHiddenVO;
import com.gewuyou.blog.common.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 获取用户菜单列表
     *
     * @return 用户菜单列表
     */
    List<UserMenuDTO> listUserMenus();

    /**
     * 获取菜单列表
     *
     * @param conditionVO 条件
     * @return 菜单列表
     */
    List<MenuDTO> listMenus(ConditionVO conditionVO);

    /**
     * 保存或更新菜单
     *
     * @param menuVO 菜单信息
     */
    void saveOrUpdateMenu(MenuVO menuVO);

    /**
     * 更新菜单是否隐藏
     *
     * @param isHiddenVO 菜单是否隐藏信息
     */
    void updateMenuIsHidden(IsHiddenVO isHiddenVO);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    void deleteMenu(Integer menuId);

    /**
     * 获取角色菜单选项列表
     *
     * @return 角色菜单选项列表
     */
    List<LabelOptionDTO> listRoleMenuOptions();
}
