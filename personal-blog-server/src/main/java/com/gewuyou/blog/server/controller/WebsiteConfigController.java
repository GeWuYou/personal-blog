package com.gewuyou.blog.server.controller;


import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.IWebsiteConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网站配置表 前端控制器
 *
 * @author gewuyou
 * @since 2024-05-05
 */
@Tag(name = "网站配置表 前端控制器", description = "网站配置表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/website")
public class WebsiteConfigController {
    private final IWebsiteConfigService websiteConfigService;

    @Autowired
    public WebsiteConfigController(IWebsiteConfigService websiteConfigService) {
        this.websiteConfigService = websiteConfigService;
    }

    /**
     * 获取网站配置
     *
     * @return 网站配置
     */
    @Operation(summary = "获取网站配置", description = "获取网站配置")
    @GetMapping("/config")
    public Result<WebsiteConfigDTO> getWebsiteConfig() {
        return Result.success(websiteConfigService.getWebsiteConfig());
    }
}
