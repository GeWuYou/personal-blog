package com.gewuyou.blog.common.strategy.interfaces;

import com.gewuyou.blog.common.dto.EsArticleDTO;

import java.util.List;

/**
 * 检索策略
 *
 * @author gewuyou
 * @since 2024-05-15 下午3:47:47
 */
public interface SearchStrategy {
    List<EsArticleDTO> searchArticle(String keywords);
}
