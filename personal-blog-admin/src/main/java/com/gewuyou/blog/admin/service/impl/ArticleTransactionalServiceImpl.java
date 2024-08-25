package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.ArticleMapper;
import com.gewuyou.blog.admin.service.IArticleTagService;
import com.gewuyou.blog.admin.service.IArticleTransactionalService;
import com.gewuyou.blog.admin.service.ICategoryService;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.CompletableFutureUtil;
import com.gewuyou.blog.common.utils.FileUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

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
    private final IImageReferenceService imageReferenceService;
    private final Executor asyncTaskExecutor;


    @Autowired
    public ArticleTransactionalServiceImpl(
            ICategoryService categoryService,
            IArticleTagService articleTagService,
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper,
            IImageReferenceService imageReferenceService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.categoryService = categoryService;
        this.articleTagService = articleTagService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.imageReferenceService = imageReferenceService;
        this.asyncTaskExecutor = asyncTaskExecutor;
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
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        var category = categoryService.saveCategoryByArticleVO(articleVO);
        // 转换VO为实体
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        article.setUserId(UserUtil.getUserDetailsDTO().getUserInfoId());
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        // 处理图片引用
        // 查询数据库中该文章的图片image_url 和content
        Article selectArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleCover, Article::getArticleContent)
                .eq(Article::getId, articleVO.getId())
        );
        List<String> newImageUrls = new ArrayList<>();
        List<String> oldImageUrls = new ArrayList<>();
        String newArticleCover = article.getArticleCover();
        String oldArticleCover;
        if (Objects.nonNull(selectArticle)) {
            oldArticleCover = selectArticle.getArticleCover();
        } else {
            oldArticleCover = null;
        }
        newImageUrls.add(newArticleCover);
        oldImageUrls.add(oldArticleCover);
        // 提取文章中的图片url
        Set<String> oldImageUrlsSet = FileUtil.extractImageUrlsByMarkdown(selectArticle.getArticleContent());
        oldImageUrls.addAll(oldImageUrlsSet);
        Set<String> newImageUrlsSet = FileUtil.extractImageUrlsByMarkdown(article.getArticleContent());
        newImageUrls.addAll(newImageUrlsSet);
        imageReferenceService.handleImageReference(newImageUrls, oldImageUrls);
        // 保存
        this.saveOrUpdate(article);
        // 保存文章标签
        articleTagService.saveByArticleVOAndId(articleVO, article.getId());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 异步处理文章发布订阅
                // 判断文章是否公开
                if (article.getStatus().equals(PUBLIC.getStatus())) {
                    CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor, () -> {
                        try {
                            // 发布订阅消息
                            rabbitTemplate.convertAndSend(SUBSCRIBE_EXCHANGE, "*",
                                    new Message(
                                            objectMapper.writeValueAsBytes(
                                                    article.getId()), new MessageProperties()));
                        } catch (JsonProcessingException e) {
                            throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
                        }
                    });
                }
            }
        });
    }
}
