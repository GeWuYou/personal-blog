package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.TalkAdminDTO;
import com.gewuyou.blog.common.model.Talk;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 说说表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

    /**
     * 查询说说列表
     *
     * @param page        分页条件
     * @param conditionVO 条件
     * @return 说说DTO列表
     */
    Page<TalkAdminDTO> listTalkAdminDTOs(Page<TalkAdminDTO> page, @Param("conditionVO") ConditionVO conditionVO);

    /**
     * 根据ID查询说说DTO
     *
     * @param talkId 说说ID
     * @return 说说DTO
     */
    TalkAdminDTO getTalkAdminDTOById(@Param("talkId") Integer talkId);
}
