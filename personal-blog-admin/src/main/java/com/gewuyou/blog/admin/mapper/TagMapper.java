package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章ID查询标签名称列表
     *
     * @param articleId 文章ID
     * @return 标签名称列表
     */
    List<String> listTagNamesByArticleId(@Param("articleId") Long articleId);

    /**
     * 分页查询标签列表
     *
     * @param page        分页对象
     * @param conditionVO 条件对象
     * @return 分页查询结果
     */
    Page<Tag> listTags(Page<Tag> page, @Param("conditionVO") ConditionVO conditionVO);
}
