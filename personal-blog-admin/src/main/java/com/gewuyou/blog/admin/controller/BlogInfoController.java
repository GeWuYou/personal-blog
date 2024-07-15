package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IBlogInfoService;
import com.gewuyou.blog.admin.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.BlogAdminInfoDTO;
import com.gewuyou.blog.common.dto.BlogHomeInfoDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.enums.OperationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 博客信息控制器
 *
 * @author gewuyou
 * @since 2024-04-26 下午11:08:46
 */
@Tag(name = "博客信息控制器", description = "博客信息控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL)
public class BlogInfoController {
    private final IBlogInfoService blogInfoService;

    private final UploadStrategyContext uploadStrategyContext;

    @Autowired
    public BlogInfoController(
            IBlogInfoService blogInfoService,
            UploadStrategyContext uploadStrategyContext
    ) {
        this.blogInfoService = blogInfoService;
        this.uploadStrategyContext = uploadStrategyContext;
    }

    /**
     * 获取博客系统信息
     *
     * @return Result<BlogHomeInfoDTO>
     */
    @Operation(summary = "获取博客系统信息", description = "获取博客系统信息")
    @GetMapping("/system")
    public Result<BlogHomeInfoDTO> getBlogHomeInfo() {
        return Result.success(blogInfoService.getBlogHomeInfo());
    }

    /**
     * 获取博客后台信息
     *
     * @return Result<BlogAdminInfoDTO>
     */
    @Operation(summary = "获取博客后台信息", description = "获取博客后台信息")
    @GetMapping
    public Result<BlogAdminInfoDTO> getBlogBackInfo() {
        return Result.success(blogInfoService.getBlogAdminInfo());
    }

    /**
     * 上报访客信息
     *
     * @return Result<?>
     */
    @Operation(summary = "上报访客信息", description = "上报访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.success();
    }

    /**
     * 上传博客配置图片
     *
     * @param file 图片文件
     * @return Result<?>
     */
    @Parameter(name = "file", description = "图片文件", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "上传博客配置图片", description = "上传博客配置图片")
    @OperationLogging(type = OperationType.UPDATE)
    @PostMapping("/config/images")
    public Result<?> savePhotoAlbumCover(MultipartFile file) {
        return Result.success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }
}
