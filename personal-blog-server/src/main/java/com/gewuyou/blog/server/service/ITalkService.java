package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.TalkDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.Talk;

/**
 * <p>
 * 说说表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ITalkService extends IService<Talk> {

    Long selectCount();

    /**
     * 获取说说列表
     *
     * @return 说说列表
     */
    PageResult<TalkDTO> listTalkDTOs();

    /**
     * 根据说说ID获取说说详情
     *
     * @param talkId 说说ID
     * @return 说说详情
     */
    TalkDTO getTalkById(Integer talkId);
}
