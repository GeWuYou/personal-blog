package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.server.mapper.ArticleTagMapper;
import com.gewuyou.blog.server.service.IArticleTagService;
import com.gewuyou.blog.server.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 文章标签中间表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    private final ITagService tagService;


    @Autowired
    public ArticleTagServiceImpl(
            ITagService tagService
    ) {
        this.tagService = tagService;
    }

    /**
     * 根据文章VO与文章ID保存文章标签中间表数
     *
     * @param articleVO 文章VO
     * @param articleId 文章ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveByArticleVOAndId(ArticleVO articleVO, Long articleId) {
        // 判断该文章是否有关联标签,如果有则先删除
        if (Objects.nonNull(articleVO.getId())) {
            baseMapper.delete(
                    new LambdaQueryWrapper<ArticleTag>()
                            .eq(ArticleTag::getArticleId, articleId)
            );
        }
        // 获取标签名列表
        List<String> tagNames = articleVO.getTagNames();
        if (CollectionUtils.isNotEmpty(tagNames)) {
            // 先根据标签名获取数据库中对应且存在的标签列表
            List<Tag> existTags = tagService.list(
                    new LambdaQueryWrapper<Tag>()
                            .in(Tag::getTagName, tagNames)
            );
            // 获取对应的标签名列表
            List<String> existTagNames = existTags
                    .stream()
                    .map(Tag::getTagName)
                    .toList();
            // 从标签名列表中移除数据库中已经存在的标签名
            tagNames.removeAll(existTagNames);
            // 获取数据库中已有的标签id列表
            List<Long> existTagIds = new ArrayList<>(existTags
                    .stream()
                    .map(Tag::getId)
                    .toList());
            // 判断标签列表中此时是否有数据库中不存在的标签
            if (CollectionUtils.isNotEmpty(tagNames)) {
                // 为不存在的标签创建标签类
                List<Tag> noExistTags = tagNames
                        .stream()
                        .map(tagName -> Tag
                                .builder()
                                .tagName(tagName)
                                .build())
                        .toList();
                // 批量保存不存在的标签
                tagService.saveBatch(noExistTags);
                // 组合已存在的标签id列表与不存在的标签id列表
                existTagIds.addAll(noExistTags
                        .stream()
                        .map(Tag::getId)
                        .toList());
            }
            // 组合文章与标签的中间表
            List<ArticleTag> articleTags = existTagIds
                    .stream()
                    .map(tagId -> ArticleTag
                            .builder()
                            .articleId(articleId)
                            .tagId(tagId)
                            .build())
                    .toList();
            // 批量保存文章与标签的中间表
            this.saveBatch(articleTags);
        }
    }
}
