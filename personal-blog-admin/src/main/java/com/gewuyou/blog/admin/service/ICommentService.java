package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ReviewVO;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 获取后台评论列表
     *
     * @param conditionVO 条件
     * @return 评论列表
     */
    PageResultDTO<CommentAdminDTO> listCommentsAdminDTOs(ConditionVO conditionVO);

    /**
     * 审核评论
     *
     * @param reviewVO 审核VO
     */
    void updateCommentsReview(ReviewVO reviewVO);
}
