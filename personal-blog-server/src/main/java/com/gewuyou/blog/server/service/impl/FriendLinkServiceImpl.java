package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.FriendLinkDTO;
import com.gewuyou.blog.common.model.FriendLink;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.server.mapper.FriendLinkMapper;
import com.gewuyou.blog.server.service.IFriendLinkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 友链表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {

    /**
     * 获取所有友链信息
     *
     * @return 友链信息列表
     */
    @Override
    public List<FriendLinkDTO> listFriendLinkDTOs() {
        List<FriendLink> friendLinks = baseMapper.selectList(null);
        return BeanCopyUtil.copyList(friendLinks, FriendLinkDTO.class);
    }
}
