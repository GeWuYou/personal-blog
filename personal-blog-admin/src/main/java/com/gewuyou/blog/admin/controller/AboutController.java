package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IAboutService;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.AboutVO;
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
 * 关于表 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-05-05
 */
@Tag(name = "关于表 前端控制器", description = "关于表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/about")
public class AboutController {
    public final IAboutService aboutService;

    @Autowired
    public AboutController(IAboutService aboutService) {
        this.aboutService = aboutService;
    }


    /**
     * 更新关于信息
     *
     * @param aboutVO 关于信息
     * @return 响应信息
     */
    @Operation(summary = "更新关于信息", description = "更新关于信息")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping
    @Idempotent
    public Result<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        aboutService.updateAbout(aboutVO);
        return Result.success();
    }
}
