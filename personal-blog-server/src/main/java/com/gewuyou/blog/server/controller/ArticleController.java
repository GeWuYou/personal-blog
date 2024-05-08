package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.ArticleRankDTO;
import com.gewuyou.blog.server.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/article")
public class ArticleController {
    private final IArticleService articleService;

    @Autowired
    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/count/not-deleted")
    public Long selectCountNotDeleted() {
        return articleService.selectCountNotDeleted();
    }

    @PostMapping("/rank")
    public List<ArticleRankDTO> listArticleRank(@RequestBody Map<Object, Double> articleMap) {
        return articleService.listArticleRank(articleMap);
    }
}
