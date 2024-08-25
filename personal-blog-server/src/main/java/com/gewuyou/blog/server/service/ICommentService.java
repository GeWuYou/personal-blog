package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.dto.CommentDTO;
import com.gewuyou.blog.common.dto.ReplyDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.common.vo.CommentVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ReviewVO;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ICommentService extends IService<Comment> {

    Long selectCommentCountByType(Byte type);

    /**
     * 保存评论
     *
     * @param commentVO 评论信息
     */
    void saveComment(CommentVO commentVO);

    /**
     * 根据评论VO获取评论列表
     *
     * @param commentVO 评论VO
     * @return 评论列表
     */
    PageResult<CommentDTO> listCommentDTOs(CommentVO commentVO);

    /**
     * 根据评论ID获取回复列表
     *
     * @param commentId 评论ID
     * @return replies
     */
    List<ReplyDTO> listReplyDTOsByCommentId(Long commentId);

    /**
     * 获取前六条评论
     *
     * @return 评论列表
     */
    List<CommentDTO> listTopSixCommentDTOs();

    /**
     * 获取后台评论列表
     *
     * @param conditionVO 条件
     * @return 评论列表
     */
    PageResult<CommentAdminDTO> listCommentsAdminDTOs(ConditionVO conditionVO);

    /**
     * 审核评论
     *
     * @param reviewVO 审核VO
     */
    void updateCommentsReview(ReviewVO reviewVO);
}
