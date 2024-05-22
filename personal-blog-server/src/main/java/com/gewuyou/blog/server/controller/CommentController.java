package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.annotation.AccessLimit;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.dto.CommentDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.ReplyDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.CommentVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ReviewVO;
import com.gewuyou.blog.server.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "<p> 评论表 前端控制器 </p>", description = "<p> 评论表 前端控制器 </p>")
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
    public Result<PageResultDTO<CommentDTO>> getComments(CommentVO commentVO) {
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
     * 查询后台评论
     *
     * @param conditionVO 条件VO
     * @return Result
     */
    @Operation(summary = "查询后台评论", description = "查询后台评论")
    @GetMapping("/admin/list")
    public Result<PageResultDTO<CommentAdminDTO>> listCommentBackDTO(ConditionVO conditionVO) {
        return Result.success(commentService.listCommentsAdminDTOs(conditionVO));
    }

    /**
     * 审核评论
     *
     * @param reviewVO 审核VO
     * @return Result
     */
    @Operation(summary = "审核评论", description = "审核评论")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/admin/review")
    public Result<?> updateCommentsReview(@Validated @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.success();
    }

    /**
     * 删除评论
     *
     * @param commentIdList 评论id列表
     * @return Result
     */
    @Operation(summary = "删除评论", description = "删除评论")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/admin")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

    @Parameter(name = "type", description = "", in = ParameterIn.PATH, required = true)
    @Operation(summary = "", description = "")
    @GetMapping("/count/type/{type}")
    public Long selectCommentCountByType(@PathVariable("type") Byte type) {
        return commentService.selectCommentCountByType(type);
    }
}
