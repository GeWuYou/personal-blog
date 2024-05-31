package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PhotoDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 照片 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/photo")
public class PhotoController {
    private final IPhotoService photoService;

    @Autowired
    public PhotoController(IPhotoService photoService) {
        this.photoService = photoService;
    }

    /**
     * 根据相册Id查看照片列表
     *
     * @param albumId 相册Id
     * @return 照片列表
     */
    @GetMapping("/{albumId}")
    public Result<PhotoDTO> listPhotosByAlbumId(@PathVariable("albumId") Integer albumId) {
        return Result.success(photoService.listPhotoDTOsByAlbumId(albumId));
    }
}
