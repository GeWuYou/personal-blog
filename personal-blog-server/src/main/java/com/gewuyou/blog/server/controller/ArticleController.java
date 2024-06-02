package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.vo.ArticleAccessPasswordVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.server.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "<p> 文章表 前端控制器 </p>", description = "<p> 文章表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/article")
public class ArticleController {
    private final IArticleService articleService;

    @Autowired
    public ArticleController(
            IArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 获取置顶和推荐的文章
     *
     * @return TopAndFeaturedArticlesDTO
     */
    @Operation(summary = "获取置顶和推荐的文章", description = "获取置顶和推荐的文章")
    @GetMapping("/top-and-featured")
    public Result<TopAndFeaturedArticlesDTO> listTopAndFeaturedArticles() {
        return Result.success(articleService.listTopAndFeaturedArticles());
    }

    /**
     * 获取文章列表
     *
     * @return PageResultDTO<ArticleRankDTO>
     */
    @Operation(summary = "获取文章列表", description = "获取文章列表")
    @GetMapping("/list")
    public Result<PageResultDTO<ArticleCardDTO>> listArticles() {
        return Result.success(articleService.listArticleCardDTOs());
    }


    /**
     * 根据文章分类id获取文章列表
     *
     * @param capacityId 文章分类id
     * @return PageResultDTO<ArticleCardDTO>
     */
    @Parameter(name = "capacityId", description = "文章分类id", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "根据文章分类id获取文章列表", description = "根据文章分类id获取文章列表")
    @GetMapping("/list/capacityId")
    public Result<PageResultDTO<ArticleCardDTO>> listArticlesByCategoryId(@RequestParam Long capacityId) {
        return Result.success(articleService.listArticleCardDTOsByCategoryId(capacityId));
    }

    /**
     * 根据文章id获取文章
     *
     * @param articleId 文章id
     * @return ArticleDTO
     */
    @Parameter(name = "articleId", description = "文章id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据文章id获取文章", description = "根据文章id获取文章")
    @GetMapping("/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Long articleId) {
        return Result.success(articleService.getArticleDTOById(articleId));
    }

    /**
     * 校验文章访问密码
     *
     * @param articleAccessPasswordVO 文章访问密码VO
     * @return Result
     */
    @Operation(summary = "校验文章访问密码", description = "校验文章访问密码")
    @PostMapping("/verify")
    public Result<?> verifyArticleAccessPassword(@Valid @RequestBody ArticleAccessPasswordVO articleAccessPasswordVO) {
        articleService.verifyArticleAccessPassword(articleAccessPasswordVO);
        return Result.success();
    }

    /**
     * 根据标签id获取文章列表
     *
     * @param tagId 标签id
     * @return PageResultDTO<ArticleCardDTO>
     */
    @Parameter(name = "tagId", description = "标签id", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "根据标签id获取文章列表", description = "根据标签id获取文章列表")
    @GetMapping("/list/tagId")
    public Result<PageResultDTO<ArticleCardDTO>> listArticlesByTagId(@RequestParam Long tagId) {
        return Result.success(articleService.listArticleCardDTOsByTagId(tagId));
    }

    /**
     * 获取所有文章归档
     *
     * @return PageResultDTO<ArchiveDTO>
     */
    @Operation(summary = "获取所有文章归档", description = "获取所有文章归档")
    @GetMapping("/list/archives")
    public Result<PageResultDTO<ArchiveDTO>> listArchives() {
        return Result.success(articleService.listArchiveDTOs());
    }

    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return Result<List < EsArticleDTO>>
     */
    @Operation(summary = "搜索文章", description = "搜索文章")
    @GetMapping("/search")
    public Result<List<EsArticleDTO>> listArticlesBySearch(ConditionVO condition) {
        return Result.success(articleService.listArticlesBySearch(condition));
    }


    /**
     * 获取文章总数(不包括删除的文章)
     *
     * @return java.lang.Long 文章总数
     * @apiNote 获取文章总数
     * @since 2024/5/19 上午11:30
     */
    @Operation(summary = "获取文章总数(不包括删除的文章)", description = "获取文章总数(不包括删除的文章)")
    @GetMapping("/count/not-deleted")
    public Long selectCountNotDeleted() {
        return articleService.selectCountNotDeleted();
    }


    /**
     * 获取文章排行
     *
     * @param articleMap 文章标题和访问量的map
     * @return List<ArticleRankDTO> 文章排行
     */
    @Operation(summary = "获取文章排行", description = "获取文章排行")
    @PostMapping("/rank")
    public List<ArticleRankDTO> listArticleRank(@RequestBody Map<Object, Double> articleMap) {
        return articleService.listArticleRank(articleMap);
    }
}
