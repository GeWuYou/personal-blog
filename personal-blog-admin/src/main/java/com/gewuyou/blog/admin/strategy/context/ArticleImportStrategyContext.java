package com.gewuyou.blog.admin.strategy.context;

import com.gewuyou.blog.admin.strategy.interfaces.ArticleImportStrategy;
import com.gewuyou.blog.common.enums.MarkdownTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * 文章导入策略上下文
 *
 * @author gewuyou
 * @since 2024-05-13 下午7:51:03
 */
@Service
public class ArticleImportStrategyContext {
    private final Map<String, ArticleImportStrategy> articleImportStrategyMap;

    @Autowired
    public ArticleImportStrategyContext(Map<String, ArticleImportStrategy> articleImportStrategyMap) {
        this.articleImportStrategyMap = articleImportStrategyMap;
    }

    /**
     * 根据类型导入文章
     *
     * @param file 文章文件
     * @param type 文章类型
     */
    public void importArticles(MultipartFile file, String type) {
        articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type)).importArticles(file);
    }
}
