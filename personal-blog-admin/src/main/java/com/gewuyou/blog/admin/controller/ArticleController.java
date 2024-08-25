package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IArticleService;
import com.gewuyou.blog.admin.service.IArticleTransactionalService;
import com.gewuyou.blog.admin.strategy.context.ArticleImportStrategyContext;
import com.gewuyou.blog.admin.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.ArticleAdminDTO;
import com.gewuyou.blog.common.dto.ArticleAdminViewDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.vo.ArticleTopFeaturedVO;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gewuyou.blog.common.enums.OperationType.*;

/**
 * 文章前端控制器
 *
 * @author gewuyou
 * @since 2024-06-01 下午9:51:57
 */
@Tag(name = "文章前端控制器", description = "文章前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/article")
public class ArticleController {
    private final IArticleTransactionalService articleTransactionalService;
    private final IArticleService articleService;
    private final UploadStrategyContext uploadStrategyContext;
    private final ArticleImportStrategyContext articleImportStrategyContext;

    @Autowired
    public ArticleController(
            IArticleTransactionalService articleTransactionalService,
            UploadStrategyContext uploadStrategyContext,
            ArticleImportStrategyContext articleImportStrategyContext,
            IArticleService articleService) {
        this.articleTransactionalService = articleTransactionalService;
        this.uploadStrategyContext = uploadStrategyContext;
        this.articleImportStrategyContext = articleImportStrategyContext;
        this.articleService = articleService;
    }

    /**
     * 获取后台文章列表
     *
     * @param conditionVO 条件VO
     * @return PageResult<ArticleAdminDTO>
     */
    @Operation(summary = "获取后台文章列表", description = "获取后台文章列表")
    @GetMapping("/list")
    public Result<PageResult<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO) {
        return Result.success(articleService.listArticlesAdminDTOs(conditionVO));
    }

    /**
     * 保存或更新文章
     *
     * @param articleVO 文章VO
     * @return Result
     */
    @Operation(summary = "保存或更新文章", description = "保存或更新文章")
    @OperationLogging(type = SAVE_OR_UPDATE, logParams = false, logResult = false)
    @PostMapping
    @Idempotent
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
    @Operation(summary = "修改文章是否置顶和推荐", description = "修改文章是否置顶和推荐")
    @OperationLogging(type = UPDATE, logParams = false, logResult = false)
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
    @Operation(summary = "删除或者恢复文章", description = "删除或者恢复文章")
    @PutMapping
    @Idempotent
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
    @Operation(summary = "物理删除文章", description = "物理删除文章")
    @OperationLogging(type = DELETE)
    @DeleteMapping
    @Idempotent
    public Result<?> deleteArticles(@RequestBody List<Long> articleIds) {
        articleService.deleteArticles(articleIds);
        return Result.success();
    }

    /**
     * 上传文章图片
     *
     * @param file 图片文件
     * @return Result<String>
     */
    @Parameter(name = "file", description = "图片文件", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "上传文章图片", description = "上传文章图片")
    @OperationLogging(type = UPLOAD, logParams = false, logResult = false)
    @PostMapping("/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        return Result.success(uploadStrategyContext
                .executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath())
                .join());
    }

    /**
     * 根据文章id获取后台文章
     *
     * @param articleId 文章id
     * @return ArticleAdminViewDTO
     */
    @Parameter(name = "articleId", description = "文章id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据文章id获取后台文章", description = "根据文章id获取后台文章")
    @GetMapping("/{articleId}")
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
    @Parameters({
            @Parameter(name = "file", description = "文件", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "type", description = "类型", in = ParameterIn.QUERY)
    })
    @Operation(summary = "导入文章", description = "导入文章")
    @OperationLogging(type = UPLOAD, logParams = false)
    @PostMapping("/import")
    @Idempotent
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
    @Operation(summary = "导出文章", description = "导出文章")
    @OperationLogging(type = EXPORT)
    @PostMapping("/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIds) {
        return Result.success(articleService
                .exportArticles(articleIds)
                .join());
    }
}
