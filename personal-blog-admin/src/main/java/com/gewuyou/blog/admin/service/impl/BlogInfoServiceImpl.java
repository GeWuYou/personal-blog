package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gewuyou.blog.admin.client.ServerClient;
import com.gewuyou.blog.admin.service.IBlogInfoService;
import com.gewuyou.blog.admin.service.IUniqueViewService;
import com.gewuyou.blog.admin.service.IWebsiteConfigService;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.IpUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    private final IWebsiteConfigService websiteConfigService;


    @Autowired
    public BlogInfoServiceImpl(
            IRedisService redisService,
            HttpServletRequest request,
            ServerClient serverClient,
            IWebsiteConfigService websiteConfigService,
            IUniqueViewService uniqueViewService
    ) {
        this.redisService = redisService;
        this.request = request;
        this.serverClient = serverClient;
        this.websiteConfigService = websiteConfigService;
        this.uniqueViewService = uniqueViewService;
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
        CompletableFuture<Long> asyncArticleCount = CompletableFuture.supplyAsync(serverClient::selectArticleCountNotDeleted);
        CompletableFuture<Long> asyncCategoryCount = CompletableFuture.supplyAsync(serverClient::selectCategoryCount);
        CompletableFuture<Long> asyncTagCount = CompletableFuture.supplyAsync(serverClient::selectTagCount);
        CompletableFuture<Long> asyncTalkCount = CompletableFuture.supplyAsync(serverClient::selectTalkCount);
        CompletableFuture<WebsiteConfigDTO> asyncWebsiteConfig = CompletableFuture.supplyAsync(websiteConfigService::getWebsiteConfig);
        CompletableFuture<Long> asyncViewCount = CompletableFuture.supplyAsync(() -> {
            Object count = redisService.get(BLOG_VIEWS_COUNT);
            return Long.parseLong(Optional.ofNullable(count).orElse(0).toString());
        });
        try {

            return BlogHomeInfoDTO.builder()
                    .articleCount(asyncArticleCount.get())
                    .categoryCount(asyncCategoryCount.get())
                    .tagCount(asyncTagCount.get())
                    .talkCount(asyncTalkCount.get())
                    .websiteConfigDTO(asyncWebsiteConfig.get())
                    .viewCount(asyncViewCount.get()).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error("获取博客后台首页信息失败", e);
            throw new GlobalException(ResponseInformation.SERVER_ERROR);
        }
    }

    /**
     * 获取博客管理后台信息
     *
     * @return 博客管理后台信息DTO
     */
    @Override
    public BlogAdminInfoDTO getBlogAdminInfo() {
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        Long viewsCount = Long.parseLong(Optional.ofNullable(count).orElse(0).toString());
        Long messageCount = serverClient.selectCommentCountByType(Byte.valueOf("2"));
        Long userCount = serverClient.selectUserInfoCount().getData();
        Long articleCount = serverClient.selectArticleCountNotDeleted();
        List<UniqueViewDTO> uniqueViews = uniqueViewService.listUniqueViews();
        List<ArticleStatisticsDTO> articleStatisticsDTOs = serverClient.listArticleStatistics().getData();
        List<CategoryDTO> categoryDTOs = serverClient.listCategories().getData();
        List<TagDTO> tagDTOs = BeanCopyUtil.copyList(serverClient.listTags().getData(), TagDTO.class);
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        BlogAdminInfoDTO auroraAdminInfoDTO = BlogAdminInfoDTO.builder()
                .articleStatisticsDTOs(articleStatisticsDTOs)
                .tagDTOs(tagDTOs)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOs(categoryDTOs)
                .uniqueViewDTOs(uniqueViews)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            List<ArticleRankDTO> articleRankDTOList = serverClient.listArticleRank(articleMap);
            auroraAdminInfoDTO.setArticleRankDTOs(articleRankDTOList);
        }
        return auroraAdminInfoDTO;
    }
}
