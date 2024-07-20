package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ArticleTagMapper;
import com.gewuyou.blog.admin.mapper.TagMapper;
import com.gewuyou.blog.admin.service.ITagService;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TagAdminDTO;
import com.gewuyou.blog.common.dto.TagOptionDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.TagVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    private final ArticleTagMapper articleTagMapper;

    public TagServiceImpl(ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
    }

    /**
     * 查询标签列表
     *
     * @param conditionVO 条件
     * @return 标签列表
     */
    @Override
    public PageResultDTO<TagAdminDTO> listTagsAdminDTOs(ConditionVO conditionVO) {
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                        Tag::getTagName, conditionVO.getKeywords()));
        if (count == 0) {
            return new PageResultDTO<>();
        }
        Page<TagAdminDTO> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        var tagAdminDTOs = baseMapper.listTags(page, conditionVO).getRecords();
        return new PageResultDTO<>(tagAdminDTOs, count);
    }

    /**
     * 根据搜索条件查询标签列表
     *
     * @param conditionVO 条件
     * @return 标签列表
     */
    @Override
    public List<TagOptionDTO> listTagsAdminDTOsBySearch(ConditionVO conditionVO) {
        List<Tag> tags = baseMapper.selectList(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()),
                        Tag::getTagName, conditionVO.getKeywords())
                .orderByDesc(Tag::getId));
        return BeanCopyUtil.copyList(tags, TagOptionDTO.class);
    }

    /**
     * 保存或更新标签
     *
     * @param tagVO 标签信息
     */
    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        Tag existTag = baseMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            throw new GlobalException(ResponseInformation.TAG_NAME_ALREADY_EXISTS);
        }
        Tag tag = BeanCopyUtil.copyObject(tagVO, Tag.class);
        try {
            this.saveOrUpdate(tag);
        } catch (Exception e) {
            throw new GlobalException(ResponseInformation.TAG_NAME_ALREADY_EXISTS);
        }
    }

    /**
     * 删除标签
     *
     * @param tagIds 标签id列表
     */
    @Override
    public void deleteTag(List<Long> tagIds) {
        Long count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, tagIds));
        if (count > 0) {
            throw new GlobalException(ResponseInformation.NON_EMPTY_TAG_DELETION_REQUEST);
        }
        baseMapper.deleteBatchIds(tagIds);
    }
}
