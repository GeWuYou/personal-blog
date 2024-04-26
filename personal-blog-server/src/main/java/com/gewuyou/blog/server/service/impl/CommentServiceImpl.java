package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.Comment;
import com.gewuyou.blog.server.mapper.CommentMapper;
import com.gewuyou.blog.server.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
