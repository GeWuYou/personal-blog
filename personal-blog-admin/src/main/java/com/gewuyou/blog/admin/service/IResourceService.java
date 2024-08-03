package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.LabelOptionDTO;
import com.gewuyou.blog.common.dto.ResourceDTO;
import com.gewuyou.blog.common.model.Resource;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ResourceVO;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
public interface IResourceService extends IService<Resource> {

    /**
     * 查询资源列表
     *
     * @param conditionVO 条件
     * @return 资源列表
     */
    List<ResourceDTO> listResourceDTOs(ConditionVO conditionVO);

    /**
     * 删除资源
     *
     * @param resourceId 资源ID
     */
    void deleteResource(Integer resourceId);

    /**
     * 保存或更新资源
     *
     * @param resourceVO 资源VO
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 查询资源选项
     *
     * @return 资源选项
     */
    List<LabelOptionDTO> listResourceOptionDTO();

    /**
     * 导入swagger接口
     */
    void importSwagger();
}
