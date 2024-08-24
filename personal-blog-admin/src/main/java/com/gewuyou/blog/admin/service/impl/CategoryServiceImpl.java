package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ArticleMapper;
import com.gewuyou.blog.admin.mapper.CategoryMapper;
import com.gewuyou.blog.admin.service.ICategoryService;
import com.gewuyou.blog.common.dto.CategoryAdminDTO;
import com.gewuyou.blog.common.dto.CategoryOptionDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.CompletableFutureUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.common.vo.CategoryVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

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

    private final ArticleMapper articleMapper;
    private final Executor asyncTaskExecutor;

    public CategoryServiceImpl(ArticleMapper articleMapper, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.articleMapper = articleMapper;
        this.asyncTaskExecutor = asyncTaskExecutor;
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
        if (Objects.isNull(category) && !DRAFT.getStatus().equals(articleVO.getStatus())) {
            category = Category.builder()
                    .categoryName(articleVO.getCategoryName())
                    .build();
            baseMapper.insert(category);
        }
        return category;
    }

    /**
     * 查询后台分类列表
     *
     * @param conditionVO 条件
     * @return 后台分类列表
     */
    @Override
    public PageResult<CategoryAdminDTO> listCategoryAdminDTOs(ConditionVO conditionVO) {
        return CompletableFutureUtil
                .supplyAsyncWithExceptionAlly(
                        () -> baseMapper.listCategories(
                                new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize()), conditionVO), asyncTaskExecutor)
                .thenApply(PageResult::new)
                .exceptionally(e -> {
                    log.error("查询后台分类列表失败", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 查询后台分类选项列表
     *
     * @param conditionVO 条件
     * @return 后台分类选项列表
     */
    @Override
    public List<CategoryOptionDTO> listCategoryOptionDTOsBySearch(ConditionVO conditionVO) {
        List<Category> categories = baseMapper
                .selectList(
                        new LambdaQueryWrapper<Category>()
                                .like(
                                        StringUtils.isNoneBlank(conditionVO.getKeywords()),
                                        Category::getCategoryName,
                                        conditionVO.getKeywords()
                                ).orderByAsc(Category::getId)
                );
        return BeanCopyUtil.copyList(categories, CategoryOptionDTO.class);
    }

    /**
     * 删除分类
     *
     * @param categoryIds 分类ID列表
     */
    @Override
    public void deleteCategories(List<Integer> categoryIds) {
        Long selectCount = articleMapper.selectCount(
                new LambdaQueryWrapper<Article>()
                        .in(Article::getCategoryId, categoryIds)
        );
        if (selectCount > 0) {
            throw new GlobalException(ResponseInformation.NON_EMPTY_CATEGORICAL_DELETION_REQUEST);
        }
        baseMapper.deleteBatchIds(categoryIds);
    }

    /**
     * 保存或更新分类
     *
     * @param categoryVO 分类VO
     */
    @Override
    @Async("asyncTaskExecutor")
    public void saveOrUpdateCategory(CategoryVO categoryVO) {
        Category existCategory = baseMapper.selectOne(
                new LambdaQueryWrapper<Category>()
                        .select(Category::getId)
                        .eq(Category::getCategoryName, categoryVO.getCategoryName())
        );
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new GlobalException(ResponseInformation.CATEGORY_NAME_ALREADY_EXISTS);
        }
        Category category = Category
                .builder()
                .categoryName(categoryVO.getCategoryName())
                .build();
        this.saveOrUpdate(category);
    }
}
