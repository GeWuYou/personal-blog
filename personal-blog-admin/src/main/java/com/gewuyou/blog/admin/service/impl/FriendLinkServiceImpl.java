package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.FriendLinkMapper;
import com.gewuyou.blog.admin.service.IFriendLinkService;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.dto.FriendLinkAdminDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.FriendLink;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.DateUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.FriendLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * 友链接服务 实现
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:21:47
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {
    private final IImageReferenceService imageReferenceService;
    private final Executor asyncTaskExecutor;

    @Autowired
    public FriendLinkServiceImpl(IImageReferenceService imageReferenceService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.imageReferenceService = imageReferenceService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 分页查询友链列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Override
    public PageResult<FriendLinkAdminDTO> listFriendLinksAdminDTOs(ConditionVO conditionVO) {
        return CompletableFuture.supplyAsync(() -> baseMapper.selectPage(new Page<>(PageUtil.getCurrent(), PageUtil.getSize()),
                        new LambdaQueryWrapper<FriendLink>()
                                .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                                        FriendLink::getLinkName, conditionVO.getKeywords())
                ), asyncTaskExecutor)
                .thenApply(friendLinkPage -> new PageResult<>(friendLinkPage
                        .getRecords()
                        .stream()
                        .map(friendLink -> {
                            var friendLinkAdminDTO = BeanCopyUtil.copyObject(friendLink, FriendLinkAdminDTO.class);
                            var createTime = friendLink.getCreateTime();
                            if (Objects.nonNull(createTime)) {
                                friendLinkAdminDTO.setCreateTime(DateUtil.convertToDate(createTime));
                            }
                            return friendLinkAdminDTO;
                        })
                        .toList(), friendLinkPage.getTotal()))
                .exceptionally(e -> {
                    log.error("async exception", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 保存或更新友链
     *
     * @param friendLinkVO 友链信息
     */
    @Override
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO) {
        FriendLink friendLink = BeanCopyUtil.copyObject(friendLinkVO, FriendLink.class);
        CompletableFuture<Void> asyncSaveOrUpdate = CompletableFuture.runAsync(
                () -> this.saveOrUpdate(friendLink), asyncTaskExecutor);
        CompletableFuture<Void> asyncHandleImageReference = CompletableFuture.supplyAsync(
                        // 获取旧的图片地址
                        () -> baseMapper
                                .selectOne(new LambdaQueryWrapper<FriendLink>()
                                        .select(FriendLink::getLinkAvatar)
                                        .eq(FriendLink::getId, friendLinkVO.getId())).getLinkAvatar())
                .thenCompose(oldLinkAvatar ->
                        // 异步执行
                        CompletableFuture.runAsync(
                                () -> imageReferenceService
                                        .handleImageReference(friendLink.getLinkAvatar(), oldLinkAvatar)
                                , asyncTaskExecutor));
        CompletableFuture
                .allOf(asyncSaveOrUpdate, asyncHandleImageReference)
                .exceptionally(e -> {
                    log.error("save or update friend link error", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                });
    }
}
