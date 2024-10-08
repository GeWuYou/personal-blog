package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.TagDTO;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.server.mapper.TagMapper;
import com.gewuyou.blog.server.service.ITagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    /**
     * 查询标签数量
     *
     * @return 标签数量
     */
    @Override
    public Long selectCount() {
        return baseMapper.selectCount(null);
    }

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    @Override
    public List<Tag> listTags() {
        return baseMapper.selectList(null);
    }

    /**
     * 查询前十个标签
     *
     * @return 前十个标签
     */
    @Override
    public List<TagDTO> listTenTagDTOs() {
        return baseMapper.listTenTagDTOs();
    }

    /**
     * 查询所有标签DTO
     *
     * @return 标签DTO列表
     */
    @Override
    public List<TagDTO> listTagDTOs() {
        return baseMapper.listTagDTOs();
    }
}
