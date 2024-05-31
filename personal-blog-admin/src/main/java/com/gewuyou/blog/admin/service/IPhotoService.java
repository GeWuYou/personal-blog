package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.PhotoAdminDTO;
import com.gewuyou.blog.common.model.Photo;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import com.gewuyou.blog.common.vo.PhotoInfoVO;
import com.gewuyou.blog.common.vo.PhotoVO;

import java.util.List;

/**
 * <p>
 * 照片 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IPhotoService extends IService<Photo> {

    /**
     * 分页查询照片列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    PageResultDTO<PhotoAdminDTO> listPhotoAdminDTOs(ConditionVO conditionVO);

    /**
     * 更新照片信息
     *
     * @param photoInfoVO 照片信息
     */
    void updatePhoto(PhotoInfoVO photoInfoVO);

    /**
     * 保存照片
     *
     * @param photoVO 照片
     */
    void savePhotos(PhotoVO photoVO);

    /**
     * 移动照片到指定相册
     *
     * @param photoVO 照片
     */
    void updatePhotosAlbum(PhotoVO photoVO);

    /**
     * 更新照片删除状态
     *
     * @param deleteVO 删除VO
     */
    void updatePhotoDelete(DeleteVO deleteVO);

    /**
     * 批量删除照片
     *
     * @param photoIds 照片ID列表
     */
    void deletePhotos(List<Integer> photoIds);
}
