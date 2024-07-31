package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.ArticleMapper;
import com.gewuyou.blog.admin.mapper.ArticleTagMapper;
import com.gewuyou.blog.admin.service.IArticleTagService;
import com.gewuyou.blog.admin.service.IArticleTransactionalService;
import com.gewuyou.blog.admin.service.ICategoryService;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gewuyou.blog.common.constant.RabbitMQConstant.SUBSCRIBE_EXCHANGE;
import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PUBLIC;

/**
 * 文章事务服务实现
 *
 * @author gewuyou
 * @since 2024-05-12 下午10:44:40
 */
@Service
public class ArticleTransactionalServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleTransactionalService {
    private final ICategoryService categoryService;
    private final IArticleTagService articleTagService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ArticleTagMapper articleTagMapper;


    @Autowired
    public ArticleTransactionalServiceImpl(
            ICategoryService categoryService,
            IArticleTagService articleTagService,
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper,
            ArticleTagMapper articleTagMapper) {
        this.categoryService = categoryService;
        this.articleTagService = articleTagService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.articleTagMapper = articleTagMapper;
    }

    /**
     * 更新文章删除状态
     *
     * @param deleteVO 删除VO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleDelete(DeleteVO deleteVO) {
        List<Article> articles = deleteVO.getIds().stream()
                .map(id -> Article.builder()
                        .id(id)
                        .isDelete(deleteVO.getIsDelete())
                        .build())
                .toList();
        updateBatchById(articles);
    }

    /**
     * 保存或更新文章
     *
     * @param articleVO 文章VO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        // 保存文章分类
        var categoryId = categoryService.saveCategoryByArticleVO(articleVO);
        // 转换VO为实体
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        article.setUserId(UserUtil.getUserDetailsDTO().getUserInfoId());
        article.setCategoryId(categoryId);
        // 保存
        this.saveOrUpdate(article);
        // 保存文章标签
        articleTagService.saveByArticleVOAndId(articleVO, article.getId());
        // 判断文章是否公开
        if (article.getStatus().equals(PUBLIC.getStatus())) {
            try {
                // 发布订阅消息
                rabbitTemplate.convertAndSend(SUBSCRIBE_EXCHANGE, "*",
                        new Message(
                                objectMapper.writeValueAsBytes(
                                        article.getId()), new MessageProperties()));
            } catch (JsonProcessingException e) {
                throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
            }
        }
    }

    /**
     * 删除文章
     *
     * @param articleIds 文章id列表
     */
    @Override
    public void deleteArticles(List<Long> articleIds) {
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, articleIds));
        baseMapper.deleteBatchIds(articleIds);
    }
}
