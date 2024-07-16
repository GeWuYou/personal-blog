package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IWebsiteConfigService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.WebsiteConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 网站配置表 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-05-05
 */
@Tag(name = "网站配置表 前端控制器", description = "网站配置表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/website")
public class WebsiteConfigController {
    private final IWebsiteConfigService websiteConfigService;

    @Autowired
    public WebsiteConfigController(IWebsiteConfigService websiteConfigService) {
        this.websiteConfigService = websiteConfigService;
    }
    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置
     * @return 响应信息
     */
    @Operation(summary = "更新网站配置", description = "更新网站配置")
    @PutMapping("/config")
    @OperationLogging(type = OperationType.UPDATE)
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        websiteConfigService.updateWebsiteConfig(websiteConfigVO);
        return Result.success();
    }
}
