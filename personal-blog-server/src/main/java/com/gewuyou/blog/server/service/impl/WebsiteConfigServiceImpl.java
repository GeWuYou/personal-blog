package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.WebsiteConfig;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.server.mapper.WebsiteConfigMapper;
import com.gewuyou.blog.server.service.IWebsiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
     * 获取网站配置
     *
     * @return 网站配置DTO
     */
    @Override
    public WebsiteConfigDTO getWebsiteConfig() {
        WebsiteConfigDTO websiteConfigDTO;
        WebsiteConfig websiteConfig = (WebsiteConfig) redisService.get(WEBSITE_CONFIG);
        if (Objects.isNull(websiteConfig)) {
            websiteConfig = baseMapper.selectById(DEFAULT_CONFIG_ID);
        }
        try {
            websiteConfigDTO = objectMapper.readValue(websiteConfig.getConfig(), WebsiteConfigDTO.class);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.GET_WEBSITE_CONFIG_FAILED);
        }
        return websiteConfigDTO;
    }
}
