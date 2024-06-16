package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TalkAdminDTO;
import com.gewuyou.blog.common.model.Talk;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.TalkVO;

import java.util.List;

/**
 * <p>
 * 说说表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ITalkService extends IService<Talk> {
    /**
     * 保存或更新说说
     *
     * @param talkVO 说说VO
     */
    void saveOrUpdateTalk(TalkVO talkVO);

    /**
     * 删除说说
     *
     * @param talkIds 说说ID列表
     */
    void deleteTalks(List<Integer> talkIds);

    /**
     * 分页查询说说列表
     *
     * @param conditionVO 条件VO
     * @return 分页结果DTO
     */
    PageResultDTO<TalkAdminDTO> listBackTalkAdminDTOs(ConditionVO conditionVO);

    /**
     * 根据ID获取后台说说DTO
     *
     * @param talkId 说说ID
     * @return 后台说说DTO
     */
    TalkAdminDTO getBackTalkById(Integer talkId);
}
