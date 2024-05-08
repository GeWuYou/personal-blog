package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.server.mapper.CommentMapper;
import com.gewuyou.blog.server.service.ICommentService;
import org.springframework.stereotype.Service;

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

    @Override
    public Long selectCommentCountByType(Byte type) {
        return baseMapper
                .selectCount(
                        new LambdaQueryWrapper<Comment>()
                                .eq(Comment::getType, type)
                );
    }
}
