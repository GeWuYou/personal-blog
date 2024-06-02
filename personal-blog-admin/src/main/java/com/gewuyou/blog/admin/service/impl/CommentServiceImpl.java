package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.CommentMapper;
import com.gewuyou.blog.admin.service.ICommentService;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ReviewVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    /**
     * 获取后台评论列表
     *
     * @param conditionVO 条件
     * @return 评论列表
     */
    @Override
    public PageResultDTO<CommentAdminDTO> listCommentsAdminDTOs(ConditionVO conditionVO) {
        CompletableFuture<Long> asyncCount =
                CompletableFuture.supplyAsync(() -> baseMapper.countComments(conditionVO));
        Page<CommentAdminDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<CommentAdminDTO> commentAdminDTOS = baseMapper.listCommentAdminDTOs(page, conditionVO);
        try {
            return new PageResultDTO<>(commentAdminDTOS, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
        }
    }

    /**
     * 审核评论
     *
     * @param reviewVO 审核VO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        List<Comment> comments = reviewVO
                .getIds()
                .stream()
                .map(id -> Comment
                        .builder()
                        .id(id)
                        .isReview(reviewVO.getIsReview())
                        .build()
                )
                .toList();
        this.updateBatchById(comments);
    }


}
