package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.WebsiteConfigMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.service.IWebsiteConfigService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.WebsiteConfig;
import com.gewuyou.blog.common.service.IRedisService;
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
    private final IImageReferenceService imageReferenceService;


    @Autowired
    public WebsiteConfigServiceImpl(
            IRedisService redisService,
            ObjectMapper objectMapper,
            IImageReferenceService imageReferenceService) {
        this.redisService = redisService;
        this.objectMapper = objectMapper;
        this.imageReferenceService = imageReferenceService;
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
            // 新配置的图片url
            List<String> newUrls = Stream.of(
                            websiteConfigVO.getAuthorAvatar(),
                            websiteConfigVO.getLogo(),
                            websiteConfigVO.getFavicon(),
                            websiteConfigVO.getAlipayQRCode(),
                            websiteConfigVO.getWeiXinQRCode(),
                            websiteConfigVO.getUserAvatar(),
                            websiteConfigVO.getTouristAvatar()
                    )// 过滤掉 null 值
                    .filter(Objects::nonNull)
                    .toList();
            // 查询旧的配置
            WebsiteConfig oldWebsiteConfig;
            // 尝试从缓存获取
            oldWebsiteConfig = (WebsiteConfig) redisService.get(WEBSITE_CONFIG);
            if (Objects.isNull(oldWebsiteConfig)) {
                // 从数据库获取
                oldWebsiteConfig = baseMapper.selectById(DEFAULT_CONFIG_ID);
            }
            WebsiteConfigDTO websiteConfigDTO = objectMapper.readValue(oldWebsiteConfig.getConfig(), WebsiteConfigDTO.class);
            // 旧配置的图片url
            List<String> oldUrls = Stream.of(
                            websiteConfigDTO.getAuthorAvatar(),
                            websiteConfigDTO.getLogo(),
                            websiteConfigDTO.getFavicon(),
                            websiteConfigDTO.getAlipayQRCode(),
                            websiteConfigDTO.getWeiXinQRCode(),
                            websiteConfigDTO.getUserAvatar(),
                            websiteConfigDTO.getTouristAvatar()
                    )// 过滤掉 null 值
                    .filter(Objects::nonNull)
                    .toList();
            // 处理图片引用
            imageReferenceService.handleImageReference(newUrls, oldUrls);
            // 更新数据库
            baseMapper.updateById(websiteConfig);
            // 删除缓存
            redisService.delete(WEBSITE_CONFIG);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.JSON_SERIALIZE_OR_DESERIALIZE_ERROR);
        }
    }
}
