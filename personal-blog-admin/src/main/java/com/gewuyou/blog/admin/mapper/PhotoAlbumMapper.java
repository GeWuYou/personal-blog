package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.PhotoAlbumAdminDTO;
import com.gewuyou.blog.common.model.PhotoAlbum;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 相册 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface PhotoAlbumMapper extends BaseMapper<PhotoAlbum> {

    /**
     * 获取相册列表
     *
     * @param page        分页对象
     * @param conditionVO 条件对象
     * @return 相册列表
     */
    List<PhotoAlbumAdminDTO> listPhotoAlbumsAdminDTOs(Page<PhotoAlbumAdminDTO> page, @Param("conditionVO") ConditionVO conditionVO);
}
