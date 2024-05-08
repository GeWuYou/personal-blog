package com.gewuyou.blog.admin.service;

import com.gewuyou.blog.common.dto.UniqueViewDTO;
import com.gewuyou.blog.common.model.UniqueView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 访问视图表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-05-04
 */
public interface IUniqueViewService extends IService<UniqueView> {

    /**
     * 获取所有唯一访问视图
     *
     * @return List<UniqueViewDTO>
     */
    List<UniqueViewDTO> listUniqueViews();
}
