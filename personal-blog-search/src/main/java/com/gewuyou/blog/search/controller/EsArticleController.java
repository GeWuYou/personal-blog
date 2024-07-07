package com.gewuyou.blog.search.controller;

import com.gewuyou.blog.common.dto.EsArticleDTO;
import com.gewuyou.blog.search.service.IEsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Es 文章控制器
 *
 * @author gewuyou
 * @since 2024-05-17 下午9:47:18
 */
@RestController
public class EsArticleController {

    private final IEsArticleService esArticleService;

    @Autowired
    public EsArticleController(IEsArticleService esArticleService) {
        this.esArticleService = esArticleService;
    }

    @GetMapping("/article")
    public List<EsArticleDTO> searchArticle(@RequestParam(value = "keyword") String keyword) {
        return esArticleService.searchArticle(keyword);
    }
}
