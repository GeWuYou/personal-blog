package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gewuyou.blog.admin.service.IBlogInfoService;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static com.gewuyou.blog.common.constant.CommonConstant.UNKNOWN;
import static com.gewuyou.blog.common.constant.RedisConstant.*;

/**
 * 博客信息服务实现
 *
 * @author gewuyou
 * @since 2024-04-26 下午11:11:54
 */
@Service
public class BlogInfoServiceImpl implements IBlogInfoService {

    private final IRedisService redisService;

    private final HttpServletRequest request;

    public BlogInfoServiceImpl(
            IRedisService redisService,
            HttpServletRequest request
    ) {
        this.redisService = redisService;
        this.request = request;
    }

    /**
     * 上报访客信息
     */
    @Override
    public void report() {
        String ipAddress = IpUtils.getIpAddress(request);
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                String ipProvince = IpUtils.getIpProvince(ipSource);
                redisService.hIncr(VISITOR_AREA, ipProvince, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }
}
