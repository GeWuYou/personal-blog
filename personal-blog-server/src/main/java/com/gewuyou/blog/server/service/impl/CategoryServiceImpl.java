package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.server.mapper.CategoryMapper;
import com.gewuyou.blog.server.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.gewuyou.blog.common.enums.ArticleStatusEnum.DRAFT;

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
     * 根据文章VO保存分类
     *
     * @param articleVO 文章VO
     * @return 分类
     */
    @Override
    public Category saveCategoryByArticleVO(ArticleVO articleVO) {
        // 查询是否存在该分类
        Category category = baseMapper.selectOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryName, articleVO.getCategoryName())
        );
        // 如果不存在，并且文章状态不是草稿，则创建新分类并保存
        if (Objects.isNull(category) && !articleVO.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder()
                    .categoryName(articleVO.getCategoryName())
                    .build();
            baseMapper.insert(category);
        }
        return category;
    }
}
