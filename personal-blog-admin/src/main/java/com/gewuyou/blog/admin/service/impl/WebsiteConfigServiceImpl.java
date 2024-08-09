package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.WebsiteConfigMapper;
import com.gewuyou.blog.admin.service.IWebsiteConfigService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.WebsiteConfig;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.FileUtil;
import com.gewuyou.blog.common.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.gewuyou.blog.common.constant.CommonConstant.DEFAULT_CONFIG_ID;
import static com.gewuyou.blog.common.constant.RedisConstant.WEBSITE_CONFIG;

/**
 * <p>
 * 网站配置表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig> implements IWebsiteConfigService {

    private final IRedisService redisService;

    private final ObjectMapper objectMapper;


    @Autowired
    public WebsiteConfigServiceImpl(
            IRedisService redisService,
            ObjectMapper objectMapper
    ) {
        this.redisService = redisService;
        this.objectMapper = objectMapper;
    }

    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置VO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        try {
            WebsiteConfig websiteConfig =
                    WebsiteConfig
                            .builder()
                            .id(DEFAULT_CONFIG_ID)
                            .config(objectMapper.writeValueAsString(websiteConfigVO))
                            .build();
            baseMapper.updateById(websiteConfig);
            // 过滤掉 null 值
            List<String> urls = Stream.of(
                            websiteConfigVO.getAlipayQRCode(),
                            websiteConfigVO.getFavicon(),
                            websiteConfigVO.getAuthorAvatar(),
                            websiteConfigVO.getLogo(),
                            websiteConfigVO.getTouristAvatar(),
                            websiteConfigVO.getUserAvatar(),
                            websiteConfigVO.getWeiXinQRCode()
                    ).filter(Objects::nonNull)
                    .toList();

            // 将非空值保存到 Redis 中
            if (!urls.isEmpty()) {
                redisService.sAdd(RedisConstant.DB_IMAGE_NAME, urls.stream().map(FileUtil::getFilePathByUrl).toArray());
            }
            // 删除缓存
            redisService.delete(WEBSITE_CONFIG);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.INSUFFICIENT_PERMISSIONS);
        }
    }
}
