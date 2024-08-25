package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.CommentAdminDTO;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 根据条件查询后台评论列表
     *
     * @param page        分页对象
     * @param conditionVO 条件VO
     * @return 评论列表
     */
    Page<CommentAdminDTO> listCommentAdminDTOs(Page<CommentAdminDTO> page, @Param("conditionVO") ConditionVO conditionVO);
}
