package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.FriendLinkDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.IFriendLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 友链表 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "友链表 前端控制器", description = "友链表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/link")
public class FriendLinkController {
    private final IFriendLinkService friendLinkService;

    @Autowired
    public FriendLinkController(IFriendLinkService friendLinkService) {
        this.friendLinkService = friendLinkService;
    }

    /**
     * 查看友链列表
     *
     * @return Result
     */
    @Operation(summary = "查看友链列表", description = "查看友链列表")
    @GetMapping("/list")
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        return Result.success(friendLinkService.listFriendLinkDTOs());
    }


}
