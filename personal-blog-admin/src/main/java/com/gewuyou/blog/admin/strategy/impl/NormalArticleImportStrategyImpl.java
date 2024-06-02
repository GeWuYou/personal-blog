package com.gewuyou.blog.admin.strategy.impl;

import com.gewuyou.blog.admin.service.IArticleTransactionalService;
import com.gewuyou.blog.admin.strategy.interfaces.ArticleImportStrategy;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.gewuyou.blog.common.enums.ArticleStatusEnum.DRAFT;


/**
 * 普通文章导入策略实现
 *
 * @author gewuyou
 * @since 2024-05-06 下午10:00:08
 */
@Slf4j
@Service("normalArticleImportStrategyImpl")
public class NormalArticleImportStrategyImpl implements ArticleImportStrategy {


    private final IArticleTransactionalService articleTransactionalService;

    public NormalArticleImportStrategyImpl(IArticleTransactionalService articleTransactionalService) {
        this.articleTransactionalService = articleTransactionalService;
    }

    /**
     * 导入文章
     *
     * @param file 导入的文件
     */
    @Override
    public void importArticles(MultipartFile file) {
        String articleTitle = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];
        StringBuilder articleContent = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (reader.ready()) {
                articleContent.append(reader.read());
            }
        } catch (Exception e) {
            log.error("导入文章失败", e);
            throw new GlobalException(ResponseInformation.IMPORT_ARTICLE_FAILED);
        }
        ArticleVO articleVO = ArticleVO.builder()
                .articleTitle(articleTitle)
                .articleContent(articleContent.toString())
                .status(DRAFT.getStatus())
                .build();
        articleTransactionalService.saveOrUpdateArticle(articleVO);
    }
}
