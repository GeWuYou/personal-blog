package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.WebsiteConfigMapper;
import com.gewuyou.blog.admin.service.IWebsiteConfigService;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.WebsiteConfig;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (websiteConfig == null) {
            websiteConfigDTO = objectMapper.convertValue(baseMapper.selectById(DEFAULT_CONFIG_ID), WebsiteConfigDTO.class);
        } else {
            websiteConfigDTO = objectMapper.convertValue(websiteConfig, WebsiteConfigDTO.class);
        }
        return websiteConfigDTO;
    }

    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置VO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        try {
            WebsiteConfig websiteConfig =
                    WebsiteConfig
                            .builder()
                            .id(DEFAULT_CONFIG_ID)
                            .config(objectMapper.writeValueAsString(websiteConfigVO))
                            .build();
            baseMapper.updateById(websiteConfig);
            // 删除缓存
            redisService.delete(WEBSITE_CONFIG);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.INSUFFICIENT_PERMISSIONS);
        }
    }
}
