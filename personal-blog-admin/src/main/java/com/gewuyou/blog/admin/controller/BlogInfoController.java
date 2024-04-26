package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IBlogInfoService;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客信息控制器
 *
 * @author gewuyou
 * @since 2024-04-26 下午11:08:46
 */
@RestController
@RequestMapping(InterfacePermissionConstant.BASE_URL)
public class BlogInfoController {
    private final IBlogInfoService blogInfoService;

    @Autowired
    public BlogInfoController(
            IBlogInfoService blogInfoService
    ) {
        this.blogInfoService = blogInfoService;
    }

    @Operation(description = "上报访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.success();
    }
}
