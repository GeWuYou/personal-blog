package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gewuyou.blog.admin.client.ServerClient;
import com.gewuyou.blog.admin.service.IBlogInfoService;
import com.gewuyou.blog.admin.service.IUniqueViewService;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.IpUtil;
import com.gewuyou.blog.common.utils.RedisUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.CommonConstant.UNKNOWN;
import static com.gewuyou.blog.common.constant.RedisConstant.*;

/**
 * 博客信息服务实现
 *
 * @author gewuyou
 * @since 2024-04-26 下午11:11:54
 */
@Service
@Slf4j
public class BlogInfoServiceImpl implements IBlogInfoService {

    private final IRedisService redisService;

    private final HttpServletRequest request;

    private final ServerClient serverClient;

    private final IUniqueViewService uniqueViewService;

    private final Executor asyncTaskExecutor;


    @Autowired
    public BlogInfoServiceImpl(
            IRedisService redisService,
            HttpServletRequest request,
            ServerClient serverClient,
            IUniqueViewService uniqueViewService,
            @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor
    ) {
        this.redisService = redisService;
        this.request = request;
        this.serverClient = serverClient;
        this.uniqueViewService = uniqueViewService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 上报访客信息
     */
    @Override
    public void report() {
        String ipAddress = IpUtil.getIpAddress(request);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                String ipProvince = IpUtil.getIpProvince(ipSource);
                redisService.hIncr(VISITOR_AREA, ipProvince, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    /**
     * 获取博客后台首页信息
     *
     * @return 博客后台首页信息DTO
     */
    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        CompletableFuture<Long> asyncArticleCount = CompletableFuture.supplyAsync(serverClient.selectArticleCountNotDeleted()::getData, asyncTaskExecutor);
        CompletableFuture<Long> asyncCategoryCount = CompletableFuture.supplyAsync(serverClient.selectCategoryCount()::getData, asyncTaskExecutor);
        CompletableFuture<Long> asyncTagCount = CompletableFuture.supplyAsync(serverClient.selectTagCount()::getData, asyncTaskExecutor);
        CompletableFuture<Long> asyncTalkCount = CompletableFuture.supplyAsync(serverClient.selectTalkCount()::getData, asyncTaskExecutor);
        CompletableFuture<WebsiteConfigDTO> asyncWebsiteConfig = CompletableFuture.supplyAsync(serverClient.getWebsiteConfig()::getData, asyncTaskExecutor);
        CompletableFuture<Long> asyncViewCount = CompletableFuture.supplyAsync(() -> RedisUtil.getLongValue(redisService.get(BLOG_VIEWS_COUNT)), asyncTaskExecutor);
        CompletableFuture<Void> asyncAllOf = CompletableFuture.allOf(asyncArticleCount, asyncCategoryCount, asyncTagCount, asyncTalkCount, asyncWebsiteConfig, asyncViewCount);
        return asyncAllOf
                .exceptionally(e -> {
                    log.error("获取博客后台首页信息失败", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                })
                .thenApply(v -> BlogHomeInfoDTO.builder()
                        .articleCount(asyncArticleCount.join())
                        .categoryCount(asyncCategoryCount.join())
                        .tagCount(asyncTagCount.join())
                        .talkCount(asyncTalkCount.join())
                        .websiteConfigDTO(asyncWebsiteConfig.join())
                        .viewCount(asyncViewCount.join()).build()).join();
    }

    /**
     * 获取博客管理后台信息
     *
     * @return 博客管理后台信息DTO
     */
    @Override
    public BlogAdminInfoDTO getBlogAdminInfo() {
        CompletableFuture<Long> asyncViewCount = CompletableFuture.supplyAsync(() -> RedisUtil.getLongValue(redisService.get(BLOG_VIEWS_COUNT)), asyncTaskExecutor);
        CompletableFuture<Long> asyncArticleCount = CompletableFuture.supplyAsync(serverClient.selectArticleCountNotDeleted()::getData, asyncTaskExecutor);
        CompletableFuture<Long> asyncMessageCount = CompletableFuture.supplyAsync(serverClient.selectCommentCountByType(Byte.valueOf("2"))::getData, asyncTaskExecutor);
        CompletableFuture<Long> asyncUserCount = CompletableFuture.supplyAsync(serverClient.selectUserInfoCount()::getData, asyncTaskExecutor);
        CompletableFuture<List<UniqueViewDTO>> asyncUniqueView = CompletableFuture.supplyAsync(uniqueViewService::listUniqueViews, asyncTaskExecutor);
        CompletableFuture<List<ArticleStatisticsDTO>> asyncArticleStatistics = CompletableFuture.supplyAsync(serverClient.listArticleStatistics()::getData, asyncTaskExecutor);
        CompletableFuture<List<CategoryDTO>> asyncCategoryDTOs = CompletableFuture.supplyAsync(serverClient.listCategories()::getData, asyncTaskExecutor);
        CompletableFuture<List<TagDTO>> asyncTagDTOs = CompletableFuture.supplyAsync(() -> BeanCopyUtil.copyList(serverClient.listTags().getData(), TagDTO.class), asyncTaskExecutor);
        CompletableFuture<Map<Long, Double>> asyncArticleViews = CompletableFuture.supplyAsync(() -> redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        // 解决redis默认将数字key转为Integer类型的问题
                        entry -> RedisUtil.getLongValue(entry.getKey()),
                        Map.Entry::getValue
                )), asyncTaskExecutor);
        // 等待所有任务完成
        CompletableFuture<Void> asyncAllOf = CompletableFuture.allOf(asyncArticleCount, asyncMessageCount, asyncUserCount, asyncViewCount, asyncUniqueView, asyncArticleStatistics, asyncCategoryDTOs, asyncTagDTOs, asyncArticleViews);
        // 任务完成后，获取结果
        return asyncAllOf
                .exceptionally(e -> {
                    log.error("获取博客管理后台信息失败", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                })
                .thenApply(v -> {
                    BlogAdminInfoDTO auroraAdminInfoDTO = BlogAdminInfoDTO.builder()
                            .articleStatisticsDTOs(asyncArticleStatistics.join())
                            .tagDTOs(asyncTagDTOs.join())
                            .viewsCount(asyncViewCount.join())
                            .messageCount(asyncMessageCount.join())
                            .userCount(asyncUserCount.join())
                            .articleCount(asyncArticleCount.join())
                            .categoryDTOs(asyncCategoryDTOs.join())
                            .uniqueViewDTOs(asyncUniqueView.join())
                            .build();
                    Map<Long, Double> articleMap = asyncArticleViews.join();
                    if (CollectionUtils.isNotEmpty(articleMap)) {
                        CompletableFuture<List<ArticleRankDTO>> asyncArticleRankDTOs = CompletableFuture.supplyAsync(serverClient.listArticleRank(articleMap)::getData, asyncTaskExecutor);
                        auroraAdminInfoDTO.setArticleRankDTOs(asyncArticleRankDTOs.join());
                    } else {
                        auroraAdminInfoDTO.setArticleRankDTOs(List.of());
                    }
                    return auroraAdminInfoDTO;
                }).join();
    }
}
