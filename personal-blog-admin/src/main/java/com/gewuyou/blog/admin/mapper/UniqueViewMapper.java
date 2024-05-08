package com.gewuyou.blog.admin.mapper;

import com.gewuyou.blog.common.dto.UniqueViewDTO;
import com.gewuyou.blog.common.model.UniqueView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-05-04
 */
@Mapper
public interface UniqueViewMapper extends BaseMapper<UniqueView> {
    /**
     * 获取所有唯一访问视图
     *
     * @return List<UniqueViewDTO>
     */
    List<UniqueViewDTO> listUniqueViews();
}
