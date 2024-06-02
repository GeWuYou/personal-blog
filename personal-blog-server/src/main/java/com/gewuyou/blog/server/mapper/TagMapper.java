package com.gewuyou.blog.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.dto.TagDTO;
import com.gewuyou.blog.common.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 查询前十个标签
     *
     * @return 前十个标签
     */
    List<TagDTO> listTenTagDTOs();

    /**
     * 查询所有标签
     * @return 所有标签
     */
    List<TagDTO> listTagDTOs();
}
