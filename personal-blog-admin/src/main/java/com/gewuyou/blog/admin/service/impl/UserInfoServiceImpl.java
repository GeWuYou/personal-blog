package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.UserAuthMapper;
import com.gewuyou.blog.admin.mapper.UserInfoMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.service.IUserInfoService;
import com.gewuyou.blog.admin.service.IUserRoleService;
import com.gewuyou.blog.admin.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.dto.UserOnlineDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.model.UserRole;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.*;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.UserDisableVO;
import com.gewuyou.blog.common.vo.UserRoleVO;
import com.gewuyou.blog.security.service.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 用户信息服务实现
 *
 * @author gewuyou
 * @since 2024-07-04 下午9:23:27
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private final IUserRoleService userRoleService;
    private final UserAuthMapper userAuthMapper;
    private final JwtService jwtService;
    private final IRedisService redisService;
    private final UploadStrategyContext uploadStrategyContext;
    private final IImageReferenceService imageReferenceService;
    private final Executor asyncTaskExecutor;

    @Autowired
    public UserInfoServiceImpl(IUserRoleService userRoleService,
                               UserAuthMapper userAuthMapper,
                               JwtService jwtService,
                               IRedisService redisService,
                               UploadStrategyContext uploadStrategyContext,
                               IImageReferenceService imageReferenceService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.userRoleService = userRoleService;
        this.userAuthMapper = userAuthMapper;
        this.jwtService = jwtService;
        this.redisService = redisService;
        this.uploadStrategyContext = uploadStrategyContext;
        this.imageReferenceService = imageReferenceService;
        this.asyncTaskExecutor = asyncTaskExecutor;
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
     * 更新用户角色
     *
     * @param userRoleVO 用户角色VO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        var userInfo = UserInfo.builder()
                .id(userRoleVO.getUserInfoId())
                .nickName(userRoleVO.getNickname())
                .build();
        baseMapper.updateById(userInfo);
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getUserInfoId())
        );
        var userRoles = userRoleVO.getRoleIds()
                .stream()
                .map(
                        roleId -> UserRole.builder()
                                .userId(userRoleVO.getUserInfoId())
                                .roleId(roleId)
                                .build()
                ).toList();
        userRoleService.saveBatch(userRoles);
    }

    /**
     * 更新用户禁用状态
     *
     * @param userDisableVO 用户禁用VO
     */
    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        removeOnlineUser(userDisableVO.getId());
        var userInfo = UserInfo.builder()
                .id(userDisableVO.getId())
                .isDisable(userDisableVO.getIsDisable())
                .build();
        baseMapper.updateById(userInfo);
    }

    /**
     * 分页查询在线用户
     *
     * @param conditionVO 条件VO
     * @return 分页结果
     */
    @Override
    public PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO) {
        var userMap = redisService.hGetAll(RedisConstant.LOGIN_USER);
        var values = userMap.values();
        var userDetailDTOs = values
                .stream()
                .map(
                        value -> (UserDetailsDTO) value
                ).toList();
        List<UserOnlineDTO> onlineUsers = userDetailDTOs
                .stream()
                .map(
                        userDetailDTO -> {
                            var userOnlineDTO = BeanCopyUtil.copyObject(userDetailDTO, UserOnlineDTO.class);
                            var lastLoginTime = userDetailDTO.getLastLoginTime();
                            if (lastLoginTime != null) {
                                userOnlineDTO.setLastLoginTime(DateUtil.convertToDate(lastLoginTime));
                            }
                            return userOnlineDTO;
                        }
                )
                .filter(item -> StringUtils.isBlank(conditionVO.getKeywords()) ||
                        item.getNickname().contains(conditionVO.getKeywords()))
                .sorted(Comparator.comparing(UserOnlineDTO::getLastLoginTime).reversed())
                .toList();
        var fromIndex = PageUtil.getLimitCurrent().intValue();
        var size = PageUtil.getSize().intValue();
        var toIndex = onlineUsers.size() - fromIndex > size ? fromIndex + size : onlineUsers.size();
        var result = onlineUsers.subList(fromIndex, toIndex);
        return new PageResult<>(result, (long) onlineUsers.size());
    }

    /**
     * 移除在线用户
     *
     * @param id 用户ID
     */
    @Override
    @Async("asyncTaskExecutor")
    public void removeOnlineUser(Long id) {
        var userId = userAuthMapper.selectOne(
                new LambdaQueryWrapper<UserAuth>()
                        .eq(UserAuth::getUserInfoId, id)
        ).getId();
        jwtService.deleteLoginUser(userId);
        jwtService.deleteToken(userId);
    }

    /**
     * 更新用户头像
     *
     * @param file 头像文件
     * @return 头像url
     */
    @Override
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public String updateUserAvatar(MultipartFile file) {
        var newAvatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath()).join();
        var userInfo = UserInfo
                .builder()
                .id(UserUtil.getUserDetailsDTO().getUserInfoId())
                .avatar(newAvatar)
                .build();
        CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                () ->
                        imageReferenceService.handleImageReference(userInfo.getAvatar(), baseMapper
                                .selectOne(new LambdaQueryWrapper<UserInfo>()
                                        .select(UserInfo::getAvatar)
                                        .eq(UserInfo::getId, userInfo.getId()))
                                .getAvatar()),
                () -> baseMapper.updateById(userInfo));
        return newAvatar;
    }

}
