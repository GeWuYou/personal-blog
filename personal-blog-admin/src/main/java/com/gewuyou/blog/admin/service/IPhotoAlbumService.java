package com.gewuyou.blog.admin.service;

import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.PhotoAlbumAdminDTO;
import com.gewuyou.blog.common.dto.PhotoAlbumDTO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.PhotoAlbumVO;

import java.util.List;

/**
 * 相册服务类
 *
 * @author gewuyou
 * @since 2024-05-30 下午10:43:40
 */
public interface IPhotoAlbumService {
    /**
     * 保存或更新相册
     *
     * @param photoAlbumVO 相册VO
     */
    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    /**
     * 获取后台相册列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    PageResultDTO<PhotoAlbumAdminDTO> listPhotoAlbumsAdminDTOs(ConditionVO conditionVO);

    /**
     * 获取后台相册信息列表
     *
     * @return 相册信息列表
     */
    List<PhotoAlbumDTO> listPhotoAlbumAdminDTOs();

    /**
     * 根据相册ID获取后台相册信息
     *
     * @param albumId 相册ID
     * @return 相册信息
     */
    PhotoAlbumAdminDTO getPhotoAlbumByIdAdminDTO(Integer albumId);

    /**
     * 根据相册ID删除相册
     *
     * @param albumId 相册ID
     */
    void deletePhotoAlbumById(Integer albumId);
}
