package com.gewuyou.blog.admin.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.config.entity.QQConfigProperties;
import com.gewuyou.blog.admin.mapper.UserAuthMapper;
import com.gewuyou.blog.admin.mapper.UserInfoMapper;
import com.gewuyou.blog.admin.mapper.UserRoleMapper;
import com.gewuyou.blog.admin.service.impl.UserDetailsServiceImpl;
import com.gewuyou.blog.common.dto.QQTokenDTO;
import com.gewuyou.blog.common.dto.QQUserInfoDTO;
import com.gewuyou.blog.common.dto.SocialTokenDTO;
import com.gewuyou.blog.common.dto.SocialUserInfoDTO;
import com.gewuyou.blog.common.enums.LoginTypeEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.utils.CommonUtil;
import com.gewuyou.blog.common.vo.QQLoginVO;
import com.gewuyou.blog.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.gewuyou.blog.common.constant.SocialLoginConstant.*;

/**
 * QQ登录策略实现
 *
 * @author gewuyou
 * @since 2024-07-18 上午11:16:47
 */
@Slf4j
@Service("qqLoginStrategyImpl")
public class QQLoginStrategyImpl extends AbstractSocialLoginStrategyImpl {
    private final QQConfigProperties qqConfigProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public QQLoginStrategyImpl(
            UserAuthMapper userAuthMapper,
            UserRoleMapper userRoleMapper,
            UserDetailsServiceImpl userDetailsService,
            JwtService jwtService,
            HttpServletRequest request,
            UserInfoMapper userInfoMapper,
            QQConfigProperties qqConfigProperties, ObjectMapper objectMapper, RestTemplate restTemplate) {
        super(userAuthMapper, userRoleMapper, userDetailsService, jwtService, request, userInfoMapper);
        this.qqConfigProperties = qqConfigProperties;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public SocialTokenDTO getSocialToken(Object data) {
        QQLoginVO qqLoginVO = (QQLoginVO) data;
        checkQQToken(qqLoginVO);
        return SocialTokenDTO.builder()
                .openId(qqLoginVO.getOpenId())
                .accessToken(qqLoginVO.getAccessToken())
                .loginType(LoginTypeEnum.QQ.getType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        Map<String, String> formData = new HashMap<>(3);
        formData.put(QQ_OPEN_ID, socialTokenDTO.getOpenId());
        formData.put(ACCESS_TOKEN, socialTokenDTO.getAccessToken());
        formData.put(OAUTH_CONSUMER_KEY, qqConfigProperties.getAppId());
        String jsonString = restTemplate.getForObject(qqConfigProperties.getUserInfoUrl(), String.class, formData);
        try {
            var qqUserInfoDTO = objectMapper.readValue(
                    jsonString, QQUserInfoDTO.class);
            return SocialUserInfoDTO.builder()
                    .nickname(Objects.requireNonNull(qqUserInfoDTO).getNickname())
                    .avatar(qqUserInfoDTO.getFigureurl_qq_1())
                    .build();
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.JSON_PARSE_ERROR);
        }
    }

    private void checkQQToken(QQLoginVO qqLoginVO) {
        Map<String, String> qqData = new HashMap<>(1);
        qqData.put(ACCESS_TOKEN, qqLoginVO.getAccessToken());
        try {
            String jsonString = restTemplate.getForObject(qqConfigProperties.getCheckTokenUrl(), String.class, qqData);
            QQTokenDTO qqTokenDTO = objectMapper.readValue(CommonUtil.getBracketsContent(Objects.requireNonNull(jsonString)), QQTokenDTO.class);
            if (!qqLoginVO.getOpenId().equals(qqTokenDTO.getOpenid())) {
                throw new GlobalException(ResponseInformation.QQ_LOGIN_ERROR);
            }
        } catch (Exception e) {
            log.error("check qq token error", e);
            throw new GlobalException(ResponseInformation.QQ_LOGIN_ERROR);
        }
    }
}
