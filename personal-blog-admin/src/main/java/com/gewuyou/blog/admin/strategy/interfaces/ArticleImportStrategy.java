package com.gewuyou.blog.admin.strategy.interfaces;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文章导入策略
 *
 * @author gewuyou
 * @since 2024-05-06 下午9:59:17
 */
public interface ArticleImportStrategy {
    /**
     * 导入文章
     *
     * @param file 导入的文件
     */
    void importArticles(MultipartFile file);
}
