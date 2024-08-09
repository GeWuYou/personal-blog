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
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.FriendLink;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.DateUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.FriendLinkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 友链接服务 实现
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:21:47
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {
    private final IImageReferenceService imageReferenceService;

    @Autowired
    public FriendLinkServiceImpl(IRedisService redisService, IImageReferenceService imageReferenceService) {
        this.imageReferenceService = imageReferenceService;
    }

    /**
     * 分页查询友链列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Override
    public PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdminDTOs(ConditionVO conditionVO) {
        Page<FriendLink> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<FriendLink> friendLinkPage = baseMapper.selectPage(page,
                new LambdaQueryWrapper<FriendLink>()
                        .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                                FriendLink::getLinkName, conditionVO.getKeywords())
        );
        List<FriendLinkAdminDTO> friendLinkAdminDTOList = friendLinkPage
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
                .toList();
        return new PageResultDTO<>(friendLinkAdminDTOList, friendLinkPage.getTotal());
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
        String newLinkAvatar = friendLink.getLinkAvatar();
        String oldLinkAvatar = baseMapper
                .selectOne(new LambdaQueryWrapper<FriendLink>()
                        .select(FriendLink::getLinkAvatar)
                        .eq(FriendLink::getId, friendLinkVO.getId())).getLinkAvatar();
        imageReferenceService.handleImageReference(newLinkAvatar, oldLinkAvatar);
        this.saveOrUpdate(friendLink);
    }
}
