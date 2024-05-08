package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户信息ID查询菜单列表
     *
     * @param userInfoId 用户信息ID
     * @return 菜单列表
     */
    List<Menu> listMenusByUserInfoId(@Param("userInfoId") Long userInfoId);
}
