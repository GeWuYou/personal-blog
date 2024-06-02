package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.TagDTO;
import com.gewuyou.blog.common.model.Tag;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ITagService extends IService<Tag> {

    /**
     * 查询标签数量
     *
     * @return 标签数量
     */
    Long selectCount();

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    List<Tag> listTags();

    /**
     * 查询前十个标签
     *
     * @return 前十个标签
     */
    List<TagDTO> listTenTagDTOs();

    /**
     * 查询所有标签DTO
     *
     * @return 标签DTO列表
     */
    List<TagDTO> listTagDTOs();
}
