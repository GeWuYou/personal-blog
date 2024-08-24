package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.CompletableFutureUtil;
import com.gewuyou.blog.common.utils.DateUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ArticleAccessPasswordVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.server.mapper.ArticleMapper;
import com.gewuyou.blog.server.mapper.ArticleTagMapper;
import com.gewuyou.blog.server.service.IArticleService;
import com.gewuyou.blog.server.strategy.context.SearchStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

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
    private final SearchStrategyContext searchStrategyContext;
    private final Executor asyncTaskExecutor;


    @Autowired
    public ArticleServiceImpl(
            IRedisService redisService,
            ArticleTagMapper articleTagMapper,
            SearchStrategyContext searchStrategyContext,
            @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.redisService = redisService;
        this.articleTagMapper = articleTagMapper;
        this.searchStrategyContext = searchStrategyContext;
        this.asyncTaskExecutor = asyncTaskExecutor;
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
    public List<ArticleRankDTO> listArticleRank(Map<Long, Double> articleMap) {
        List<Long> articleIds = new ArrayList<>(articleMap.size());
        articleMap.forEach((k, v) -> articleIds.add(k));
        return baseMapper
                .selectList(
                        new LambdaQueryWrapper<Article>()
                                .select(Article::getId, Article::getArticleTitle)
                                .in(Article::getId, articleIds))
                .stream()
                .map(
                        article -> ArticleRankDTO.builder()
                                .articleTitle(article.getArticleTitle())
                                .viewsCount(articleMap.get(article.getId()).longValue())
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
            return TopAndFeaturedArticlesDTO
                    .builder()
                    .featuredArticles(List.of())
                    .build();
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
    public PageResult<ArticleCardDTO> listArticleCardDTOs() {
        return CompletableFuture.supplyAsync(
                        () -> baseMapper.listArticleCardDTOs(
                                new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize())), asyncTaskExecutor)
                .thenApply(PageResult::new)
                .exceptionally(e -> {
                    log.error("获取文章列表失败!", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 根据分类id查询文章列表
     *
     * @param categoryId 分类id
     * @return 文章列表卡片DTO
     */
    @Override
    public PageResult<ArticleCardDTO> listArticleCardDTOsByCategoryId(Long categoryId) {
        return CompletableFuture.supplyAsync(
                        () -> baseMapper.selectCount(new LambdaQueryWrapper<Article>()
                                .eq(Article::getIsDelete, FALSE)
                                .eq(Article::getCategoryId, categoryId)
                                .in(Article::getStatus, PUBLIC.getStatus(), PRIVATE.getStatus())), asyncTaskExecutor
                ).thenCombine(
                        CompletableFuture.supplyAsync(
                                () -> baseMapper
                                        .listArticleCardDTOsByCategoryId(
                                                new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize()), categoryId)
                                        .getRecords(), asyncTaskExecutor),
                        (count, articleCardDTOS) -> new PageResult<>(articleCardDTOS, count)
                ).exceptionally(e -> {
                    log.error("async task error", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                })
                .join();
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
                        .eq(Article::getId, articleId)
        );
        if (Objects.isNull(article)) {
            return null;
        }
        // 判断文章是否是私密的
        if (article.getStatus().equals(PRIVATE.getStatus())) {
            try {
                boolean isAccess = redisService.sIsMember(ARTICLE_ACCESS + UserUtil.getUserDetailsDTO().getUserAuthId(), articleId);
                if (!isAccess) {
                    throw new GlobalException(ResponseInformation.ARTICLE_PASSWORD_AUTHENTICATION_FAILED);
                }
            } catch (Exception e) {
                throw new GlobalException(ResponseInformation.ARTICLE_PASSWORD_AUTHENTICATION_FAILED);
            }

        }
        // 更新文章访问量
        CompletableFutureUtil.runAsyncWithExceptionAlly(() -> this.updateArticleViewsCount(articleId), asyncTaskExecutor);
        // 获取当前文章和与下一篇文章
        ArticleDTO articleDTO = baseMapper.getArticleCardDTOById(articleId);
        if (Objects.isNull(articleDTO)) {
            return null;
        }
        return CompletableFuture.supplyAsync(
                        // 获取上一篇文章
                        () -> {
                            ArticleCardDTO preArticleDTO = baseMapper.getPreArticleCardDTOById(articleId);
                            if (Objects.isNull(preArticleDTO)) {
                                preArticleDTO = baseMapper.getLastArticleCardDTO();
                            }
                            return preArticleDTO;
                        }, asyncTaskExecutor)
                .thenCombine(
                        CompletableFuture.supplyAsync(
                                // 获取下一篇文章
                                () -> {
                                    ArticleCardDTO nextArticleDTO = baseMapper.getNextArticleCardDTOById(articleId);
                                    if (Objects.isNull(nextArticleDTO)) {
                                        nextArticleDTO = baseMapper.getFirstArticleCardDTO();
                                    }
                                    return nextArticleDTO;
                                }, asyncTaskExecutor), (preArticleDTO, nextArticleDTO) -> {
                            articleDTO.setPreArticleCard(preArticleDTO);
                            articleDTO.setNextArticleCard(nextArticleDTO);
                            Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
                            if (Objects.nonNull(score)) {
                                articleDTO.setViewCount(score.longValue());
                            }
                            return articleDTO;
                        })
                .exceptionally(e -> {
                    log.error("async task error", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                })
                .join();
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
                        .eq(Article::getId, articleAccessPasswordVO.getArticleId())
        );
        if (Objects.isNull(article)) {
            throw new GlobalException(ResponseInformation.ARTICLE_NOT_EXIST);
        }
        if (article.getPassword().equals(articleAccessPasswordVO.getArticlePassword())) {
            // 验证通过将文章id加入到redis中作为已访问文章
            redisService.sAdd(ARTICLE_ACCESS + UserUtil.getUserDetailsDTO().getUserAuthId(), article.getId());
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
    public PageResult<ArticleCardDTO> listArticleCardDTOsByTagId(Long tagId) {
        return CompletableFuture.supplyAsync(
                        () -> articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                                .eq(ArticleTag::getTagId, tagId)), asyncTaskExecutor)
                .thenCombine(CompletableFuture.supplyAsync(
                                () -> baseMapper.listArticleCardDTOsByTagId(
                                        new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize()), tagId), asyncTaskExecutor),
                        (count, articleCardDTOS) -> new PageResult<>(articleCardDTOS, count))
                .exceptionally(e -> {
                    log.error("async task error", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                })
                .join();
    }

    /**
     * 查询文章归档
     *
     * @return 文章归档DTO
     */
    @Override
    public PageResult<ArchiveDTO> listArchiveDTOs() {
        return CompletableFuture
                .supplyAsync(
                        () -> baseMapper.listArticleCardDTOs(
                                new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize()))
                        , asyncTaskExecutor)
                .thenApply(
                        articleCardDTOS -> new PageResult<>(articleCardDTOS
                                .getRecords()
                                .stream()
                                // 将ArticleCardDTO按年份和月份进行分组
                                .collect(Collectors.groupingBy(
                                        articleCardDTO ->
                                        {
                                            LocalDateTime createTime = DateUtil.convertToLocalDateTime(articleCardDTO.getCreateTime());
                                            // 返回年份-月份字符串
                                            return createTime.getYear() + "-" + createTime.getMonth().getValue();
                                        }
                                ))
                                // 将分组后的结果转换为ArchiveDTO列表
                                .entrySet()
                                .stream()
                                .map(entry ->
                                        ArchiveDTO.builder()
                                                .Time(entry.getKey())
                                                .articles(entry.getValue())
                                                .build()
                                )
                                .sorted(
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
                                )
                                .toList(), articleCardDTOS.getTotal())
                )
                .exceptionally(e -> {
                    log.error("获取文章归档失败!", e);
                    return new PageResult<>();
                })
                .join();
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

    /**
     * 查询文章统计信息
     *
     * @return 文章统计信息
     */
    @Override
    public List<ArticleStatisticsDTO> listArticleStatistics() {
        return baseMapper.listArticleStatistics();
    }


    private void updateArticleViewsCount(Long articleId) {
        redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
    }
}
