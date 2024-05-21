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
    @GetMapping("/{commentId}/replies")
    public Result<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Long commentId) {
        return Result.success(commentService.listReplyDTOsByCommentId(commentId));
    }

    /**
     * 获取前六条评论
     *
     * @return Result
     */
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
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/admin")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

    @GetMapping("/count/type/{type}")
    public Long selectCommentCountByType(@PathVariable("type") Byte type) {
        return commentService.selectCommentCountByType(type);
    }
}
