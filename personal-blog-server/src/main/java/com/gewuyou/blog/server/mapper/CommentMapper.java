package com.gewuyou.blog.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.dto.CommentCountDTO;
import com.gewuyou.blog.common.dto.CommentDTO;
import com.gewuyou.blog.common.dto.ReplyDTO;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.common.vo.CommentVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询评论列表
     *
     * @param page      分页对象
     * @param commentVO 评论VO
     * @return 评论列表
     */
    List<CommentDTO> listCommentDTOs(Page<CommentDTO> page, @Param("commentVO") CommentVO commentVO);


    /**
     * 查询评论的回复列表
     *
     * @param commentIds 评论ID列表
     * @return 回复列表
     */
    List<ReplyDTO> listReplyDTOs(@Param("commentIds") List<Long> commentIds);

    /**
     * 获取前六条评论
     *
     * @return 评论列表
     */
    List<CommentDTO> listTopSixCommentDTOs();

    /**
     * 根据条件查询评论数量
     *
     * @param conditionVO 条件VO
     * @return 评论数量
     */
    Long countComments(@Param("conditionVO") ConditionVO conditionVO);

    /**
     * 根据条件查询后台评论列表
     *
     * @param page        分页对象
     * @param conditionVO 条件VO
     * @return 评论列表
     */
    List<CommentAdminDTO> listCommentAdminDTOs(Page<CommentAdminDTO> page, @Param("conditionVO") ConditionVO conditionVO);

    /**
     * 根据类型和主题ID查询评论数量
     *
     * @param type    评论类型
     * @param talkIds 主题ID列表
     * @return 评论数量列表
     */
    List<CommentCountDTO> listCommentCountByTypeAndTopicIds(@Param("type") Byte type, @Param("topicIds") List<Integer> talkIds);

    /**
     * 根据类型和主题ID查询评论数量
     *
     * @param type   评论类型
     * @param talkId 主题ID
     * @return 评论数量
     */
    CommentCountDTO listCommentCountByTypeAndTopicId(@Param("type") Byte type, @Param("talkId") Integer talkId);
}
