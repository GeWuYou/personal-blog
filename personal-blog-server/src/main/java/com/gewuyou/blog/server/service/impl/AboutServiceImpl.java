package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.AboutDTO;
import com.gewuyou.blog.common.model.About;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.server.mapper.AboutMapper;
import com.gewuyou.blog.server.service.IAboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.gewuyou.blog.common.constant.CommonConstant.DEFAULT_ABOUT_ID;
import static com.gewuyou.blog.common.constant.RedisConstant.ABOUT;

/**
 * <p>
 * 关于表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-05-05
 */
@Service
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements IAboutService {

    private final ObjectMapper objectMapper;

    private final IRedisService redisService;

    @Autowired
    public AboutServiceImpl(
            ObjectMapper objectMapper,
            IRedisService redisService
    ) {
        this.objectMapper = objectMapper;
        this.redisService = redisService;
    }

    /**
     * 获取关于信息
     *
     * @return AboutDTO
     */
    @Override
    public AboutDTO getAbout() {
        try {
            String content;
            // 先尝试从缓存中获取
            Object about = redisService.get(ABOUT);
            if (Objects.nonNull(about)) {
                content = (String) about;
            }
            // 缓存中没有，从数据库中获取
            else {
                content = objectMapper.readValue(baseMapper.selectById(DEFAULT_ABOUT_ID).getContent(), String.class);
                // 写入缓存
                redisService.set(ABOUT, content);
            }
            return AboutDTO
                    .builder()
                    .content(content)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
