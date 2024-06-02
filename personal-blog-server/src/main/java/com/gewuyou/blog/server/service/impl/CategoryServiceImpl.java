package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.server.mapper.CategoryMapper;
import com.gewuyou.blog.server.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

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


    @Override
    public Long selectCount() {
        return baseMapper.selectCount(null);
    }

    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    @Override
    public List<CategoryDTO> listCategoryDTOs() {
        return baseMapper.listCategoryDTOs();
    }
}
