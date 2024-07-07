package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.EmailVO;
import com.gewuyou.blog.common.vo.SubscribeVO;
import com.gewuyou.blog.common.vo.UserInfoVO;
import com.gewuyou.blog.server.mapper.UserInfoMapper;
import com.gewuyou.blog.server.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.gewuyou.blog.common.constant.RedisConstant.USER_CODE_KEY;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {


    private final UploadStrategyContext uploadStrategyContext;

    private final IRedisService redisService;

    public UserInfoServiceImpl(UploadStrategyContext uploadStrategyContext, IRedisService redisService) {
        this.uploadStrategyContext = uploadStrategyContext;
        this.redisService = redisService;
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserInfo selectUserInfoById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 插入用户信息
     *
     * @param userInfo 用户信息
     */
    @Override
    public void insert(UserInfo userInfo) {
        baseMapper.insert(userInfo);
    }

    /**
     * 获取用户数量
     *
     * @return 用户数量
     */
    @Override
    public Long selectCount() {
        return baseMapper.selectCount(null);
    }

    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        var userInfo = UserInfo.builder()
                .id(UserUtil.getUserDetailsDTO().getUserInfoId())
                .nickName(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .website(userInfoVO.getWebsite())
                .build();
        baseMapper.updateById(userInfo);
    }

    /**
     * 更新用户头像
     *
     * @param file 头像文件
     * @return 头像url
     */
    @Override
    public String updateUserAvatar(MultipartFile file) {
        var avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        var userInfo = UserInfo
                .builder()
                .id(UserUtil.getUserDetailsDTO().getUserInfoId())
                .avatar(avatar)
                .build();
        baseMapper.updateById(userInfo);
        return avatar;
    }

    /**
     * 保存用户邮箱
     *
     * @param emailVO 邮箱信息
     */
    @Override
    public void saveUserEmail(EmailVO emailVO) {
        // 判断验证码是否存在与是否正确
        var verifyCode = redisService.get(USER_CODE_KEY + emailVO.getEmail()).toString();
        if (Objects.isNull(verifyCode) || !verifyCode.equals(emailVO.getCode())) {
            throw new IllegalArgumentException("验证码错误");
        }
        var userInfo = UserInfo.builder()
                .id(UserUtil.getUserDetailsDTO().getUserInfoId())
                .email(emailVO.getEmail())
                .build();
        baseMapper.updateById(userInfo);
    }

    /**
     * 更新用户订阅状态
     *
     * @param subscribeVO 订阅信息
     */
    @Override
    public void updateUserSubscribe(SubscribeVO subscribeVO) {
        var queryUserInfo = baseMapper
                .selectOne(
                        new LambdaQueryWrapper<UserInfo>()
                                .eq(UserInfo::getId, subscribeVO.getUserInfoId())
                );
        if (StringUtils.isEmpty(queryUserInfo.getEmail())) {
            throw new IllegalArgumentException("请先绑定邮箱");
        }
        var userInfo = UserInfo.builder()
                .id(subscribeVO.getUserInfoId())
                .isSubscribe(subscribeVO.getIsSubscribe())
                .build();
        baseMapper.updateById(userInfo);
    }

    /**
     * 根据用户信息id查询用户信息
     *
     * @param userInfoId 用户信息id
     * @return 用户信息DTO
     */
    @Override
    public UserInfoDTO getUserInfoById(Long userInfoId) {
        var userInfo = baseMapper.selectById(userInfoId);
        return BeanCopyUtil.copyObject(userInfo, UserInfoDTO.class);
    }


}
