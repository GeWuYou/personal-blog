package com.gewuyou.blog.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.FriendLinkAdminDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.FriendLink;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.FriendLinkVO;

/**
 * 友链服务类
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:21:05
 */
public interface IFriendLinkService extends IService<FriendLink> {
    /**
     * 分页查询友链列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdminDTOs(ConditionVO conditionVO);

    /**
     * 保存或更新友链
     *
     * @param friendLinkVO 友链信息
     */
    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);
}
