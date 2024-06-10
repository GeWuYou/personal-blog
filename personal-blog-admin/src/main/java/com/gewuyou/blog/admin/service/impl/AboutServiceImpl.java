package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.AboutMapper;
import com.gewuyou.blog.admin.service.IAboutService;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.About;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.vo.AboutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 更新关于信息
     *
     * @param aboutVO AboutVO
     */
    @Override
    public void updateAbout(AboutVO aboutVO) {
        try {
            About about =
                    About
                            .builder()
                            .id(DEFAULT_ABOUT_ID)
                            .content(objectMapper.writeValueAsString(aboutVO.getContent()))
                            .build();
            baseMapper.updateById(about);
            // 清除缓存
            redisService.delete(ABOUT);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
        }
    }
}
