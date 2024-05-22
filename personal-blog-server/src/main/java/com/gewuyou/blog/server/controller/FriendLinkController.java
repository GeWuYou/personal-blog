package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.FriendLinkDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.IFriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 友链表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
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
    @GetMapping("/list")
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        return Result.success(friendLinkService.listFriendLinkDTOs());
    }


}
