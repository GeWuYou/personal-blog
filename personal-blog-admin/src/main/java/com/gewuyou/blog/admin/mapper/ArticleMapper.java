package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.ArticleAdminDTO;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据条件分页查询文章列表
     *
     * @param page        分页对象
     * @param conditionVO 条件VO
     * @return 文章卡片列表DTO
     */
    Page<ArticleAdminDTO> listArticlesAdmins(Page<ArticleAdminDTO> page, @Param("conditionVO") ConditionVO conditionVO);
}
