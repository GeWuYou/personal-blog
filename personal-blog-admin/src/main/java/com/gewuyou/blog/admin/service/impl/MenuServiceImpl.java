package com.gewuyou.blog.admin.service.impl;


import com.gewuyou.blog.admin.mapper.MenuMapper;
import com.gewuyou.blog.admin.service.IMenuService;
import com.gewuyou.blog.common.model.Menu;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

}
