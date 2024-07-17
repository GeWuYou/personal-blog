package com.gewuyou.blog.client;

import com.gewuyou.blog.common.dto.EsArticleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 查询客户端
 *
 * @author gewuyou
 * @since 2024-05-18 下午4:03:34
 */
@FeignClient(name = "personal-blog-search", url = "http://localhost:8083")
public interface SearchClient {
    /**
     * 搜索文章
     *
     * @param keyword 关键字
     * @return 文章列表
     */
    @GetMapping("/article")
    List<EsArticleDTO> searchArticle(String keyword);
}
