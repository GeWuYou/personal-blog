package com.gewuyou.blog.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.TalkDTO;
import com.gewuyou.blog.common.model.Talk;
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
     * 分页查询说说列表
     *
     * @param page 分页对象
     * @return 说说列表
     */
    Page<TalkDTO> listTalkDTOs(Page<TalkDTO> page);

    /**
     * 根据说说id查询说说DTO
     *
     * @param talkId 说说id
     * @return 说说DTO
     */
    TalkDTO getTalkDTOById(@Param("talkId") Integer talkId);
}
