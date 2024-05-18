package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.vo.*;
import com.gewuyou.blog.server.service.IArticleService;
import com.gewuyou.blog.server.service.IArticleTransactionalService;
import com.gewuyou.blog.server.strategy.context.ArticleImportStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.gewuyou.blog.common.enums.OperationType.*;

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
    private final IArticleTransactionalService articleTransactionalService;
    private final UploadStrategyContext uploadStrategyContext;
    private final ArticleImportStrategyContext articleImportStrategyContext;

    @Autowired
    public ArticleController(
            IArticleService articleService,
            IArticleTransactionalService articleTransactionalService,
            UploadStrategyContext uploadStrategyContext,
            ArticleImportStrategyContext articleImportStrategyContext) {
        this.articleService = articleService;
        this.articleTransactionalService = articleTransactionalService;
        this.uploadStrategyContext = uploadStrategyContext;
        this.articleImportStrategyContext = articleImportStrategyContext;
    }

    /**
     * 获取置顶和推荐的文章
     *
     * @return TopAndFeaturedArticlesDTO
     */
    @GetMapping("/top-and-featured")
    public Result<TopAndFeaturedArticlesDTO> listTopAndFeaturedArticles() {
        return Result.success(articleService.listTopAndFeaturedArticles());
    }

    /**
     * 获取文章列表
     *
     * @return PageResultDTO<ArticleRankDTO>
     */
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
    @PostMapping("/verify")
    public Result<?> verifyArticleAccessPassword(@Validated @RequestBody ArticleAccessPasswordVO articleAccessPasswordVO) {
        articleService.verifyArticleAccessPassword(articleAccessPasswordVO);
        return Result.success();
    }

    /**
     * 根据标签id获取文章列表
     *
     * @param tagId 标签id
     * @return PageResultDTO<ArticleCardDTO>
     */
    @GetMapping("/list/tagId")
    public Result<PageResultDTO<ArticleCardDTO>> listArticlesByTagId(@RequestParam Long tagId) {
        return Result.success(articleService.listArticleCardDTOsByTagId(tagId));
    }

    /**
     * 获取所有文章归档
     *
     * @return PageResultDTO<ArchiveDTO>
     */
    @GetMapping("/list/archives")
    public Result<PageResultDTO<ArchiveDTO>> listArchives() {
        return Result.success(articleService.listArchiveDTOs());
    }

    /**
     * 获取后台文章列表
     *
     * @param conditionVO 条件VO
     * @return PageResultDTO<ArticleAdminDTO>
     */
    @GetMapping("/admin/list")
    public Result<PageResultDTO<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO) {
        return Result.success(articleService.listArticlesAdminDTOs(conditionVO));
    }

    /**
     * 保存或更新文章
     *
     * @param articleVO 文章VO
     * @return Result
     */
    @OperationLogging(type = SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateArticle(@Validated @RequestBody ArticleVO articleVO) {
        articleTransactionalService.saveOrUpdateArticle(articleVO);
        return Result.success();
    }

    /**
     * 修改文章是否置顶和推荐
     *
     * @param articleTopFeaturedVO articleTopFeaturedVO
     * @return Result
     */
    @OperationLogging(type = UPDATE)
    @PutMapping("/top-and-featured")
    public Result<?> updateArticleTopAndFeatured(@Validated @RequestBody ArticleTopFeaturedVO articleTopFeaturedVO) {
        articleService.updateArticleTopAndFeatured(articleTopFeaturedVO);
        return Result.success();
    }

    /**
     * 删除或者恢复文章
     *
     * @param deleteVO 删除VO
     * @return Result
     */
    @PutMapping("/admin")
    public Result<?> updateArticleDelete(@Validated @RequestBody DeleteVO deleteVO) {
        articleTransactionalService.updateArticleDelete(deleteVO);
        return Result.success();
    }

    /**
     * 物理删除文章
     *
     * @param articleIds 文章id列表
     * @return Result
     */
    @OperationLogging(type = DELETE)
    @DeleteMapping("/admin/delete")
    public Result<?> deleteArticles(@RequestBody List<Long> articleIds) {
        articleTransactionalService.deleteArticles(articleIds);
        return Result.success();
    }

    /**
     * 上传文章图片
     *
     * @param file 图片文件
     * @return Result<String>
     */
    @OperationLogging(type = UPLOAD)
    @PostMapping("/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        return Result.success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }


    /**
     * 根据文章id获取后台文章
     *
     * @param articleId 文章id
     * @return ArticleAdminViewDTO
     */
    @GetMapping("/admin/list/{articleId}")
    public Result<ArticleAdminViewDTO> getArticleBackById(@PathVariable("articleId") Long articleId) {
        return Result.success(articleService.getArticleAdminViewDTOById(articleId));
    }

    /**
     * 导入文章
     *
     * @param file 文件
     * @param type 类型
     * @return Result
     */
    @OperationLogging(type = UPLOAD)
    @PostMapping("/import")
    public Result<?> importArticles(MultipartFile file, @RequestParam(required = false) String type) {
        articleImportStrategyContext.importArticles(file, type);
        return Result.success();
    }

    /**
     * 导出文章
     *
     * @param articleIds 文章id列表
     * @return Result<List < String>>
     */
    @OperationLogging(type = EXPORT)
    @PostMapping("/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIds) {
        return Result.success(articleService.exportArticles(articleIds));
    }

    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return Result<List < EsArticleDTO>>
     */
    @GetMapping("/search")
    public Result<List<EsArticleDTO>> listArticlesBySearch(ConditionVO condition) {
        return Result.success(articleService.listArticlesBySearch(condition));
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
