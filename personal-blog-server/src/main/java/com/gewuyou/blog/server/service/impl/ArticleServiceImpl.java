package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.enums.FileTypeEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ArticleAccessPasswordVO;
import com.gewuyou.blog.common.vo.ArticleTopFeaturedVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.server.mapper.ArticleMapper;
import com.gewuyou.blog.server.mapper.ArticleTagMapper;
import com.gewuyou.blog.server.mapper.CategoryMapper;
import com.gewuyou.blog.server.mapper.TagMapper;
import com.gewuyou.blog.server.service.IArticleService;
import com.gewuyou.blog.server.strategy.context.SearchStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;
import static com.gewuyou.blog.common.constant.RedisConstant.ARTICLE_ACCESS;
import static com.gewuyou.blog.common.constant.RedisConstant.ARTICLE_VIEWS_COUNT;
import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PRIVATE;
import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PUBLIC;

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
    private final ArticleTagMapper articleTagMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final UploadStrategyContext uploadStrategyContext;
    private final SearchStrategyContext searchStrategyContext;


    @Autowired
    public ArticleServiceImpl(
            IRedisService redisService,
            ArticleTagMapper articleTagMapper,
            CategoryMapper categoryMapper,
            TagMapper tagMapper,
            UploadStrategyContext uploadStrategyContext,
            SearchStrategyContext searchStrategyContext
    ) {

        this.redisService = redisService;
        this.articleTagMapper = articleTagMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.uploadStrategyContext = uploadStrategyContext;
        this.searchStrategyContext = searchStrategyContext;
    }

    /**
     * 查询未删除的文章数量
     *
     * @return 文章数量
     */
    @Override
    public Long selectCountNotDeleted() {
        return baseMapper.selectCount(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getIsDelete, FALSE)
        );
    }

    /**
     * 查询文章排行榜
     *
     * @param articleMap 文章id和评分映射
     * @return 文章排行榜
     */
    @Override
    public List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        List<Long> articleIds = new ArrayList<>(articleMap.size());
        articleMap.forEach((k, v) -> articleIds.add((Long) k));
        return baseMapper
                .selectList(
                        new LambdaQueryWrapper<Article>()
                                .select(Article::getArticleId, Article::getTitle)
                                .in(Article::getArticleId, articleIds))
                .stream()
                .map(
                        article -> ArticleRankDTO.builder()
                                .articleTitle(article.getTitle())
                                .viewsCount(articleMap.get(article.getArticleId()).longValue())
                                .build())
                .sorted(Comparator.comparing(ArticleRankDTO::getViewsCount).reversed())
                .toList();

    }


    /**
     * 获取置顶和推荐的文章
     *
     * @return 置顶和推荐的文章
     */
    @Override
    public TopAndFeaturedArticlesDTO listTopAndFeaturedArticles() {
        List<ArticleCardDTO> articleCardDTOS = baseMapper.listTopAndFeaturedArticles();
        if (articleCardDTOS.isEmpty()) {
            return new TopAndFeaturedArticlesDTO();
        } else if (articleCardDTOS.size() > 3) {
            articleCardDTOS = articleCardDTOS.subList(0, 3);
        }
        TopAndFeaturedArticlesDTO topAndFeaturedArticlesDTO = new TopAndFeaturedArticlesDTO();
        topAndFeaturedArticlesDTO.setTopArticle(articleCardDTOS.get(0));
        articleCardDTOS.remove(0);
        topAndFeaturedArticlesDTO.setFeaturedArticles(articleCardDTOS);
        return topAndFeaturedArticlesDTO;
    }

    /**
     * 查询文章
     *
     * @return 文章列表
     */
    @Override
    public PageResultDTO<ArticleCardDTO> listArticleCardDTOs() {
        // 查询文章总数
        LambdaQueryWrapper<Article> queryWrapper =
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getIsDelete, FALSE)
                        .in(Article::getStatus,
                                PUBLIC.getStatus(),
                                PRIVATE.getStatus());
        // 异步执行查询
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> baseMapper.selectCount(queryWrapper));
        Page<ArticleCardDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<ArticleCardDTO> articleRankDTOS = baseMapper.listArticleCardDTOs(page);
        try {
            return new PageResultDTO<>(articleRankDTOS, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取文章数量失败", e);
            throw new GlobalException(ResponseInformation.GET_COUNT_ERROR);
        }
    }

    /**
     * 根据分类id查询文章列表
     *
     * @param capacityId 分类id
     * @return 文章列表卡片DTO
     */
    @Override
    public PageResultDTO<ArticleCardDTO> listArticleCardDTOsByCategoryId(Long capacityId) {
        LambdaQueryWrapper<Article> queryWrapper =
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getIsDelete, FALSE)
                        .eq(Article::getCategoryId, capacityId)
                        .in(Article::getStatus, PUBLIC, PRIVATE);
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> baseMapper.selectCount(queryWrapper));
        Page<ArticleCardDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<ArticleCardDTO> articleCardDTOS = baseMapper.listArticleCardDTOsByCategoryId(page, capacityId);
        try {
            return new PageResultDTO<>(articleCardDTOS, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取文章数量失败", e);
            throw new GlobalException(ResponseInformation.GET_COUNT_ERROR);
        }
    }

    /**
     * 根据标签id查询文章
     *
     * @param articleId 标签id
     * @return 文章DTO
     */
    @Override
    public ArticleDTO getArticleDTOById(Long articleId) {
        Article article = baseMapper.selectOne(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getArticleId, articleId)
        );
        if (Objects.isNull(article)) {
            return null;
        }
        // 判断文章是否是私密的
        if (article.getStatus().equals(PRIVATE.getStatus())) {
            boolean isAccess = redisService.sIsMember(ARTICLE_ACCESS + UserUtil.getUserDetailsDTO().getUserAuthId(), articleId);
            if (!isAccess) {
                throw new GlobalException(ResponseInformation.ARTICLE_NOT_ACCESS);
            }
        }
        // 更新文章访问量
        this.updateArticleViewsCount(articleId);
        try {
            // 获取当前文章和上一篇文章与下一篇文章
            CompletableFuture<ArticleDTO> asyncArticleDTO = CompletableFuture.supplyAsync(
                    () -> baseMapper.getArticleCardDTOById(articleId));
            ArticleDTO articleDTO = asyncArticleDTO.get();
            if (Objects.isNull(articleDTO)) {
                return null;
            }
            CompletableFuture<ArticleCardDTO> asyncPreArticleDTO = CompletableFuture.supplyAsync(() -> {
                ArticleCardDTO preArticleDTO = baseMapper.getPreArticleCardDTOById(articleId);
                if (Objects.isNull(preArticleDTO)) {
                    preArticleDTO = baseMapper.getLastArticleCardDTO();
                }
                return preArticleDTO;
            });
            CompletableFuture<ArticleCardDTO> asyncNextArticleDTO = CompletableFuture.supplyAsync(() -> {
                ArticleCardDTO nextArticleDTO = baseMapper.getNextArticleCardDTOById(articleId);
                if (Objects.isNull(nextArticleDTO)) {
                    nextArticleDTO = baseMapper.getFirstArticleCardDTO();
                }
                return nextArticleDTO;
            });
            articleDTO.setPreArticleCard(asyncPreArticleDTO.get());
            articleDTO.setNextArticleCard(asyncNextArticleDTO.get());
            Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
            if (Objects.nonNull(score)) {
                articleDTO.setViewCount(score.longValue());
            }
            return articleDTO;
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取文章失败", e);
            throw new GlobalException(ResponseInformation.GET_ARTICLE_ERROR);
        }
    }

    /**
     * 验证文章访问密码
     *
     * @param articleAccessPasswordVO 文章访问VO
     */
    @Override
    public void verifyArticleAccessPassword(ArticleAccessPasswordVO articleAccessPasswordVO) {
        Article article = baseMapper.selectOne(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getArticleId, articleAccessPasswordVO.getArticleId())
        );
        if (Objects.isNull(article)) {
            throw new GlobalException(ResponseInformation.ARTICLE_NOT_EXIST);
        }
        if (article.getPassword().equals(articleAccessPasswordVO.getArticlePassword())) {
            // 验证通过将文章id加入到redis中作为已访问文章
            redisService.sAdd(ARTICLE_ACCESS + UserUtil.getUserDetailsDTO().getUserAuthId(), article.getArticleId());
        } else {
            throw new GlobalException(ResponseInformation.ARTICLE_ACCESS_PASSWORD_ERROR);
        }
    }

    /**
     * 根据标签id查询文章列表
     *
     * @param tagId 标签id
     * @return 文章列表卡片DTO
     */
    @Override
    public PageResultDTO<ArticleCardDTO> listArticleCardDTOsByTagId(Long tagId) {
        try {
            LambdaQueryWrapper<ArticleTag> queryWrapper =
                    new LambdaQueryWrapper<ArticleTag>()
                            .eq(ArticleTag::getTagId, tagId);
            CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> articleTagMapper.selectCount(queryWrapper));
            Page<ArticleCardDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
            List<ArticleCardDTO> articleCardDTOS = baseMapper.listArticleCardDTOsByTagId(page, tagId);
            return new PageResultDTO<>(articleCardDTOS, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取文章数量失败", e);
            throw new GlobalException(ResponseInformation.GET_COUNT_ERROR);
        }
    }

    /**
     * 查询文章归档
     *
     * @return 文章归档DTO
     */
    @Override
    public PageResultDTO<ArchiveDTO> listArchiveDTOs() {
        // 异步查询未删除且公开的文章数量
        LambdaQueryWrapper<Article> queryWrapper =
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getIsDelete, FALSE)
                        .eq(Article::getStatus, PUBLIC.getStatus());
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> baseMapper.selectCount(queryWrapper));
        // 分页查询文章卡片DTO
        Page<ArticleCardDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<ArticleCardDTO> articleCardDTOS = baseMapper.listArticleCardDTOs(page);
        Map<String, List<ArticleCardDTO>> map = new HashMap<>();
        // 遍历文章卡片DTO，将其按年份和月份归档
        for (ArticleCardDTO articleCardDTO : articleCardDTOS) {
            LocalDateTime createTime = articleCardDTO.getCreateTime();
            int month = createTime.getMonth().getValue();
            int year = createTime.getYear();
            String key = year + "-" + month;
            if (Objects.isNull(map.get(key))) {
                List<ArticleCardDTO> list = new ArrayList<>();
                list.add(articleCardDTO);
                map.put(key, list);
            } else {
                map.get(key).add(articleCardDTO);
            }
        }
        // 将归档信息转换为ArchiveDTO
        List<ArchiveDTO> archiveDTOS = new ArrayList<>();
        map.forEach((k, v) -> archiveDTOS.add(ArchiveDTO
                .builder()
                .Time(k)
                .articles(v)
                .build()
        ));
        // 按年份降序排序，月份升序排序
        archiveDTOS.sort(
                (o1, o2) -> {
                    String[] o1s = o1.getTime().split("=");
                    String[] o2s = o2.getTime().split("=");
                    int year1 = Integer.parseInt(o1s[0]);
                    int month1 = Integer.parseInt(o1s[1]);
                    int year2 = Integer.parseInt(o2s[0]);
                    int month2 = Integer.parseInt(o2s[1]);
                    if (year1 > year2) {
                        return -1;
                    } else if (year1 < year2) {
                        return 1;
                    } else {
                        return Integer.compare(month1, month2);
                    }
                }
        );
        // 返回分页结果
        try {
            return new PageResultDTO<>(archiveDTOS, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取文章数量失败", e);
            throw new GlobalException(ResponseInformation.GET_COUNT_ERROR);
        }
    }

    /**
     * 查询后台文章列表
     *
     * @param conditionVO 条件VO
     * @return 文章列表DTO
     */
    @Override
    public PageResultDTO<ArticleAdminDTO> listArticlesAdminDTOs(ConditionVO conditionVO) {
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(
                () -> baseMapper.countArticleAdmins(conditionVO));
        Page<ArticleAdminDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<ArticleAdminDTO> articleAdminDTOS = baseMapper.listArticlesAdminDTOs(page, conditionVO);
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);

        articleAdminDTOS.forEach(articleAdminDTO -> {
            Double viewsCount = viewsCountMap.get(articleAdminDTO.getId());
            if (Objects.nonNull(viewsCount)) {
                articleAdminDTO.setViewsCount(viewsCount.intValue());
            }
        });
        try {
            return new PageResultDTO<>(articleAdminDTOS, Long.valueOf(asyncCount.get()));
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取文章数量失败", e);
            throw new GlobalException(ResponseInformation.GET_COUNT_ERROR);
        }
    }

    /**
     * 更新文章置顶和推荐
     *
     * @param articleTopFeaturedVO 文章置顶和推荐VO
     */
    @Override
    public void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO) {
        Article article = Article.builder()
                .articleId(articleTopFeaturedVO.getId())
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
        Article article = baseMapper.selectById(articleId);
        Category category = categoryMapper.selectById(article.getCategoryId());
        String categoryName = Objects.isNull(category) ? null : category.getCategoryName();
        List<String> tagNames = tagMapper.listTagNamesByArticleId(articleId);
        ArticleAdminViewDTO articleAdminViewDTO = BeanCopyUtil.copyObject(article, ArticleAdminViewDTO.class);
        articleAdminViewDTO.setCategoryName(categoryName);
        articleAdminViewDTO.setTagNames(tagNames);
        return articleAdminViewDTO;
    }

    /**
     * 批量导出文章
     *
     * @param articleIds 文章id列表
     * @return 导出文件名集合
     */
    @Override
    public List<String> exportArticles(List<Integer> articleIds) {
        // 查询文章列表
        List<Article> articles = baseMapper.selectList(
                new LambdaQueryWrapper<Article>()
                        .select(Article::getTitle, Article::getContent)
                        .in(Article::getArticleId, articleIds)
        );
        List<String> urls = new ArrayList<>();
        // 调用上传策略上传到指定路径
        articles.forEach(article -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(article.getContent().getBytes(StandardCharsets.UTF_8));
            String url = uploadStrategyContext.executeUploadStrategy(
                    article.getTitle() + FileTypeEnum.MD.getTypeName(), inputStream, FilePathEnum.MD.getPath());
            urls.add(url);
        });
        // 返回上传的文件名集合
        return urls;
    }

    /**
     * 根据搜索条件搜索文章
     *
     * @param condition 搜索条件
     * @return 文章列表
     */
    @Override
    public List<EsArticleDTO> listArticlesBySearch(ConditionVO condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }


    private void updateArticleViewsCount(Long articleId) {
        redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
    }
}
