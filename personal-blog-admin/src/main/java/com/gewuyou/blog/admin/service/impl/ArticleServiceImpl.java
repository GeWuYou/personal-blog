package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ArticleMapper;
import com.gewuyou.blog.admin.mapper.ArticleTagMapper;
import com.gewuyou.blog.admin.mapper.CategoryMapper;
import com.gewuyou.blog.admin.mapper.TagMapper;
import com.gewuyou.blog.admin.service.IArticleService;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.dto.ArticleAdminDTO;
import com.gewuyou.blog.common.dto.ArticleAdminViewDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.enums.FileTypeEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.utils.RedisUtil;
import com.gewuyou.blog.common.vo.ArticleTopFeaturedVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.RedisConstant.ARTICLE_VIEWS_COUNT;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final IRedisService redisService;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final UploadStrategyContext uploadStrategyContext;
    private final Executor asyncTaskExecutor;
    private final ArticleTagMapper articleTagMapper;
    private final IImageReferenceService imageReferenceService;

    @Autowired
    public ArticleServiceImpl(
            IRedisService redisService,
            CategoryMapper categoryMapper,
            TagMapper tagMapper,
            UploadStrategyContext uploadStrategyContext,
            Executor asyncTaskExecutor,
            ArticleTagMapper articleTagMapper,
            IImageReferenceService imageReferenceService) {
        this.redisService = redisService;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.uploadStrategyContext = uploadStrategyContext;
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.articleTagMapper = articleTagMapper;
        this.imageReferenceService = imageReferenceService;
    }

    /**
     * 查询后台文章列表
     *
     * @param conditionVO 条件VO
     * @return 文章列表DTO
     */
    @Override
    public PageResult<ArticleAdminDTO> listArticlesAdminDTOs(ConditionVO conditionVO) {
        return CompletableFuture.supplyAsync(
                        () -> {
                            Page<ArticleAdminDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
                            return baseMapper.listArticlesAdmins(page, conditionVO);
                        }, asyncTaskExecutor
                ).thenApply(articles -> {
                    Map<Long, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT).entrySet().stream()
                            .collect(Collectors.toMap(
                                    // 解决redis默认将数字key转为Integer类型的问题
                                    entry -> RedisUtil.getLongValue(entry.getKey()),
                                    Map.Entry::getValue
                            ));
                    return new PageResult<>(articles
                            .getRecords()
                            .stream()
                            .peek(articleAdminDTO -> {
                                Double viewsCount = viewsCountMap.get(articleAdminDTO.getId());
                                if (Objects.nonNull(viewsCount)) {
                                    articleAdminDTO.setViewsCount(viewsCount.longValue());
                                }
                            })
                            .toList(), articles.getTotal());
                })
                .exceptionally(e -> {
                    log.error("获取文章列表失败", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 更新文章置顶和推荐
     *
     * @param articleTopFeaturedVO 文章置顶和推荐VO
     */
    @Override
    @Async("asyncTaskExecutor")
    public void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO) {
        Article article = Article.builder()
                .id(articleTopFeaturedVO.getId())
                .isTop(articleTopFeaturedVO.getIsTop())
                .isFeatured(articleTopFeaturedVO.getIsFeatured())
                .build();
        baseMapper.updateById(article);
    }

    /**
     * 根据文章id获取后台文章
     *
     * @param articleId 文章id
     * @return 后台文章DTO
     */
    @Override
    public ArticleAdminViewDTO getArticleAdminViewDTOById(Long articleId) {
        // 异步获取文章信息
        CompletableFuture<Article> articleFuture = CompletableFuture
                .supplyAsync(() -> baseMapper.selectById(articleId), asyncTaskExecutor);
        // 异步获取标签信息
        CompletableFuture<List<String>> tagNamesFuture = CompletableFuture.supplyAsync(
                () -> tagMapper.listTagNamesByArticleId(articleId), asyncTaskExecutor);
        // 异步获取分类信息
        CompletableFuture<String> categoryNameFuture = articleFuture.thenComposeAsync(article ->
                CompletableFuture.supplyAsync(() -> {
                    Category category = categoryMapper.selectById(article.getCategoryId());
                    return Objects.isNull(category) ? null : category.getCategoryName();
                }, asyncTaskExecutor)
        );
        // 合并结果
        return CompletableFuture.allOf(articleFuture, categoryNameFuture, tagNamesFuture)
                .thenApply(v -> {
                    // 处理结果并构建 DTO
                    Article article = articleFuture.join();
                    String categoryName = categoryNameFuture.join();
                    List<String> tagNames = tagNamesFuture.join();

                    ArticleAdminViewDTO articleAdminViewDTO = BeanCopyUtil.copyObject(article, ArticleAdminViewDTO.class);
                    articleAdminViewDTO.setCategoryName(categoryName);
                    articleAdminViewDTO.setTagNames(tagNames);

                    return articleAdminViewDTO;
                })
                .exceptionally(e -> {
                    log.error("获取信息失败", e);
                    throw new GlobalException(ResponseInformation.GET_INFO_ERROR);
                })
                .join();
    }

    /**
     * 批量导出文章
     *
     * @param articleIds 文章id列表
     * @return 导出文件名集合
     */
    @Override
    public CompletableFuture<List<String>> exportArticles(List<Integer> articleIds) {
        // 查询文章列表
        List<Article> articles = baseMapper.selectList(
                new LambdaQueryWrapper<Article>()
                        .select(Article::getArticleTitle, Article::getArticleContent)
                        .in(Article::getId, articleIds)
        );
        List<CompletableFuture<String>> futureUrls = new ArrayList<>();
        // 调用上传策略上传到指定路径
        articles.forEach(article -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(article.getArticleContent().getBytes(StandardCharsets.UTF_8));
            var url = uploadStrategyContext.executeUploadStrategy(
                    article.getArticleTitle() + FileTypeEnum.MD.getTypeName(), inputStream, FilePathEnum.MD.getPath());
            futureUrls.add(url);
        });
        // 返回上传的文件名集合
        return CompletableFuture.allOf(futureUrls
                        .toArray(new CompletableFuture[0]))
                .thenApply(
                        item -> futureUrls
                                .stream()
                                .map(CompletableFuture::join)
                                .toList()
                ).exceptionally(e -> {
                    log.error("async export articles failed", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                });
    }

    /**
     * 删除文章
     *
     * @param articleIds 文章id列表
     */
    @Override
    @Async("asyncTaskExecutor")
    public void deleteArticles(List<Long> articleIds) {
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, articleIds));
        // 检索文章图片引用并删除引用
        imageReferenceService.deleteImageReference(
                baseMapper.selectBatchIds(articleIds)
                        .stream()
                        .map(Article::getArticleCover)
                        .toList());
        baseMapper.deleteBatchIds(articleIds);
    }
}
