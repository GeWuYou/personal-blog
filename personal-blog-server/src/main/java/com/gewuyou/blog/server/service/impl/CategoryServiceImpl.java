package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.Category;
import com.gewuyou.blog.server.mapper.CategoryMapper;
import com.gewuyou.blog.server.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
