package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.server.service.ITalkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 说说表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "<p> 说说表 前端控制器 </p>", description = "<p> 说说表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/talk")
public class TalkController {
    private final ITalkService talkService;


    @Autowired
    public TalkController(
            ITalkService talkService
    ) {
        this.talkService = talkService;
    }

    @RequestMapping("/count")
    public Long selectCount() {
        return talkService.selectCount();
    }
}
