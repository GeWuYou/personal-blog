package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PhotoAlbumDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.IPhotoAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 相册 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/photo/album")
public class PhotoAlbumController {
    private final IPhotoAlbumService photoAlbumService;

    @Autowired
    public PhotoAlbumController(IPhotoAlbumService photoAlbumService) {
        this.photoAlbumService = photoAlbumService;
    }

    /**
     * 获取相册列表
     *
     * @return 相册列表
     */
    @GetMapping
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.success(photoAlbumService.listPhotoAlbumDTOs());
    }

}
