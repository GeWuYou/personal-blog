package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.annotation.AccessLimit;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.CommentDTO;
import com.gewuyou.blog.common.dto.ReplyDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.CommentVO;
import com.gewuyou.blog.server.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论表 前端控制器
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "评论表 前端控制器", description = "评论表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/comment")
public class CommentController {
    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 添加评论
     *
     * @param commentVO 评论VO
     * @return Result
     */
    @Operation(summary = "添加评论", description = "添加评论")
    @AccessLimit(seconds = 60, maxCount = 3)
    @OperationLogging(type = OperationType.SAVE)
    @PostMapping
    @Idempotent
    public Result<?> saveComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.success();
    }

    /**
     * 获取评论
     *
     * @param commentVO 评论VO
     * @return Result
     */
    @Operation(summary = "获取评论", description = "获取评论")
    @GetMapping("/list")
    public Result<PageResult<CommentDTO>> getComments(CommentVO commentVO) {
        return Result.success(commentService.listCommentDTOs(commentVO));
    }

    /**
     * 根据评论id获取回复列表
     *
     * @param commentId 评论id
     * @return Result
     */
    @Parameter(name = "commentId", description = "评论id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据评论id获取回复列表", description = "根据评论id获取回复列表")
    @GetMapping("/{commentId}/replies")
    public Result<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Long commentId) {
        return Result.success(commentService.listReplyDTOsByCommentId(commentId));
    }

    /**
     * 获取前六条评论
     *
     * @return Result
     */
    @Operation(summary = "获取前六条评论", description = "获取前六条评论")
    @GetMapping("/topSix")
    public Result<List<CommentDTO>> listTopSixComments() {
        return Result.success(commentService.listTopSixCommentDTOs());
    }


    /**
     * 根据类型获取评论数量
     *
     * @param type 类型
     * @return Long
     */
    @Parameter(name = "type", description = "类型", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据类型获取评论数量", description = "根据类型获取评论数量")
    @GetMapping("/count/type/{type}")
    public Result<Long> selectCommentCountByType(@PathVariable("type") Byte type) {
        return Result.success(commentService.selectCommentCountByType(type));
    }
}
