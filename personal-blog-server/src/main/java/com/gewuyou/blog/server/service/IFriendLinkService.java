package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.FriendLinkDTO;
import com.gewuyou.blog.common.model.FriendLink;

import java.util.List;

/**
 * <p>
 * 友链表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IFriendLinkService extends IService<FriendLink> {

    /**
     * 获取所有友链信息
     *
     * @return 友链信息列表
     */
    List<FriendLinkDTO> listFriendLinkDTOs();
}
