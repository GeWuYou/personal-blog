package com.gewuyou.blog.server.strategy.impl;

import com.gewuyou.blog.client.SearchClient;
import com.gewuyou.blog.common.dto.EsArticleDTO;
import com.gewuyou.blog.common.strategy.interfaces.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Es 搜索策略 实现
 *
 * @author gewuyou
 * @since 2024-05-18 下午4:39:41
 */
@Service("esSearchStrategyImpl")
public class EsSearchStrategyImpl implements SearchStrategy {
    private final SearchClient searchClient;

    @Autowired
    public EsSearchStrategyImpl(SearchClient searchClient) {
        this.searchClient = searchClient;
    }

    @Override
    public List<EsArticleDTO> searchArticle(String keywords) {
        return searchClient.searchArticle(keywords);
    }
}
