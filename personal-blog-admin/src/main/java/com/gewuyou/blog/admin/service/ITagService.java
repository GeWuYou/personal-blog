package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TagAdminDTO;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.TagVO;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ITagService extends IService<Tag> {


    /**
     * 查询标签列表
     *
     * @param conditionVO 条件
     * @return 标签列表
     */
    PageResultDTO<TagAdminDTO> listTagsAdminDTOs(ConditionVO conditionVO);

    /**
     * 根据搜索条件查询标签列表
     *
     * @param conditionVO 条件
     * @return 标签列表
     */
    List<TagAdminDTO> listTagsAdminDTOsBySearch(ConditionVO conditionVO);

    /**
     * 保存或更新标签
     *
     * @param tagVO 标签信息
     */
    void saveOrUpdateTag(TagVO tagVO);

    /**
     * 删除标签
     *
     * @param tagIds 标签id列表
     */
    void deleteTag(List<Long> tagIds);
}
