package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.server.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/comment")
public class CommentController {
    private final ICommentService commentService;


    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;

    }

    @GetMapping("/comment/count/type/{type}")
    public Long selectCommentCountByType(@PathVariable("type") Byte type) {
        return commentService.selectCommentCountByType(type);
    }
}
