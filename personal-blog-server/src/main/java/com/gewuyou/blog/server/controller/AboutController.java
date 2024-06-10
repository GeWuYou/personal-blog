package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.AboutDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.IAboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于我前端控制器
 *
 * @author gewuyou
 * @since 2024-06-10 下午10:25:46
 */
@Tag(name = "关于我前端控制器", description = "关于我前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/about")
public class AboutController {

    public final IAboutService aboutService;

    @Autowired
    public AboutController(IAboutService aboutService) {
        this.aboutService = aboutService;
    }

    /**
     * 获取关于信息
     *
     * @return 关于信息
     */
    @Operation(summary = "获取关于信息", description = "获取关于信息")
    @GetMapping
    public Result<AboutDTO> getAbout() {
        return Result.success(aboutService.getAbout());
    }
}
