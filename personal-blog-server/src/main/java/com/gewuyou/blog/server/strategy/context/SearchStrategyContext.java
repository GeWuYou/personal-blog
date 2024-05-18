package com.gewuyou.blog.server.strategy.context;

import com.gewuyou.blog.common.dto.EsArticleDTO;
import com.gewuyou.blog.common.strategy.interfaces.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 搜索策略上下文
 *
 * @author gewuyou
 * @since 2024-05-18 下午4:31:00
 */
@Service
public class SearchStrategyContext {
    @Value("${search.mode}")
    private String searchMode;

    private final Map<String, SearchStrategy> searchStrategyMap;

    @Autowired
    public SearchStrategyContext(Map<String, SearchStrategy> searchStrategyMap) {
        this.searchStrategyMap = searchStrategyMap;
    }

    public List<EsArticleDTO> executeSearchStrategy(String keyword) {
        return searchStrategyMap.get(searchMode).searchArticle(keyword);
    }
}
