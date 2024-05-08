package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.model.Comment;

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
}
