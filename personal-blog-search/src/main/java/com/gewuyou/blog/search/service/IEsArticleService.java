package com.gewuyou.blog.search.service;

import com.gewuyou.blog.common.dto.EsArticleDTO;

import java.util.List;

/**
 * Es 文章服务接口
 *
 * @author gewuyou
 * @since 2024-05-15 下午3:55:29
 */
public interface IEsArticleService {
    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return 文章列表
     */
    List<EsArticleDTO> searchArticle(String keywords);
}
