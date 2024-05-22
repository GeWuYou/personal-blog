package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.FriendLinkMapper;
import com.gewuyou.blog.admin.service.IFriendLinkService;
import com.gewuyou.blog.common.dto.FriendLinkAdminDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.FriendLink;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.FriendLinkVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链接服务 实现
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:21:47
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {
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
        List<FriendLinkAdminDTO> friendLinkAdminDTOList = BeanCopyUtil.copyList(friendLinkPage.getRecords(),
                FriendLinkAdminDTO.class);
        return new PageResultDTO<>(friendLinkAdminDTOList, friendLinkPage.getTotal());
    }

    /**
     * 保存或更新友链
     *
     * @param friendLinkVO 友链信息
     */
    @Override
    public void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO) {
        FriendLink friendLink = BeanCopyUtil.copyObject(friendLinkVO, FriendLink.class);
        this.saveOrUpdate(friendLink);
    }
}
