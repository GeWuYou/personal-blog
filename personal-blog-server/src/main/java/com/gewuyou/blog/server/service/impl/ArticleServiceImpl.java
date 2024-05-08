package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.ArticleRankDTO;
import com.gewuyou.blog.common.enums.ArticleStatusEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.server.mapper.ArticleMapper;
import com.gewuyou.blog.server.service.IArticleService;
import com.gewuyou.blog.server.service.IArticleTagService;
import com.gewuyou.blog.server.service.ICategoryService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;
import static com.gewuyou.blog.common.constant.RabbitMQConstant.SUBSCRIBE_EXCHANGE;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final ICategoryService categoryService;
    private final IArticleTagService articleTagService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    @Autowired
    public ArticleServiceImpl(
            ICategoryService categoryService,
            IArticleTagService articleTagService,
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper
    ) {
        this.categoryService = categoryService;
        this.articleTagService = articleTagService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 查询未删除的文章数量
     *
     * @return 文章数量
     */
    @Override
    public Long selectCountNotDeleted() {
        return baseMapper.selectCount(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getIsDelete, FALSE)
        );
    }

    /**
     * 查询文章排行榜
     *
     * @param articleMap 文章id和评分映射
     * @return 文章排行榜
     */
    @Override
    public List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        List<Long> articleIds = new ArrayList<>(articleMap.size());
        articleMap.forEach((k, v) -> articleIds.add((Long) k));
        return baseMapper
                .selectList(
                        new LambdaQueryWrapper<Article>()
                                .select(Article::getArticleId, Article::getTitle)
                                .in(Article::getArticleId, articleIds))
                .stream()
                .map(
                        article -> ArticleRankDTO.builder()
                                .articleTitle(article.getTitle())
                                .viewsCount(articleMap.get(article.getArticleId()).longValue())
                                .build())
                .sorted(Comparator.comparing(ArticleRankDTO::getViewsCount).reversed())
                .toList();

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
        categoryService.saveCategoryByArticleVO(articleVO);
        // 转换VO为实体
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        article.setUserId(UserUtil.getUserDetailsDTO().getUserInfoId());
        // 保存
        this.saveOrUpdate(article);
        // 保存文章标签
        articleTagService.saveByArticleVOAndId(articleVO, article.getArticleId());
        // 判断文章是否公开
        if (article.getStatus().equals(ArticleStatusEnum.PUBLIC.getStatus())) {
            try {
                // 发布订阅消息
                rabbitTemplate.convertAndSend(SUBSCRIBE_EXCHANGE, "*",
                        new Message(
                                objectMapper.writeValueAsBytes(
                                        article.getArticleId()), new MessageProperties()));
            } catch (JsonProcessingException e) {
                throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
            }
        }
    }

}
