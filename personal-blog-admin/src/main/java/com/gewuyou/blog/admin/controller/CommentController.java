package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.ICommentService;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ReviewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论前端控制器
 *
 * @author gewuyou
 * @since 2024-06-01 下午10:58:48
 */
@Tag(name = "评论前端控制器", description = "评论前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/comment")
public class CommentController {
    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;

    }

    /**
     * 查询后台评论
     *
     * @param conditionVO 条件VO
     * @return Result
     */
    @Operation(summary = "查询后台评论", description = "查询后台评论")
    @GetMapping("/list")
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
    @PutMapping("/review")
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
    @DeleteMapping
    @Idempotent
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }
}
