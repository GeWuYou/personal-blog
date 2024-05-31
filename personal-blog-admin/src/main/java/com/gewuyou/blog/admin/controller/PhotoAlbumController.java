package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IPhotoAlbumService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.PhotoAlbumAdminDTO;
import com.gewuyou.blog.common.dto.PhotoAlbumDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.PhotoAlbumVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 相册前端控制器
 *
 * @author gewuyou
 * @since 2024-05-30 下午10:41:14
 */
@Tag(name = "相册前端控制器", description = "相册前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/photo/album")
public class PhotoAlbumController {
    private final UploadStrategyContext uploadStrategyContext;
    private final IPhotoAlbumService photoAlbumService;


    @Autowired
    public PhotoAlbumController(UploadStrategyContext uploadStrategyContext, IPhotoAlbumService photoAlbumService) {
        this.uploadStrategyContext = uploadStrategyContext;
        this.photoAlbumService = photoAlbumService;
    }

    /**
     * 上传相册封面
     *
     * @param file 相册封面文件
     * @return 上传结果
     */
    @Parameter(name = "file", description = "相册封面文件", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "上传相册封面", description = "上传相册封面")
    @OperationLogging(type = OperationType.UPLOAD)
    @PostMapping("/upload")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    /**
     * 保存或更新相册
     *
     * @param photoAlbumVO 相册VO
     * @return 保存或更新结果
     */
    @Operation(summary = "保存或更新相册", description = "保存或更新相册")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.success();
    }

    /**
     * 查看后台相册列表
     *
     * @param conditionVO 条件VO
     * @return 相册列表
     */
    @Operation(summary = "查看后台相册列表", description = "查看后台相册列表")
    @GetMapping
    public Result<PageResultDTO<PhotoAlbumAdminDTO>> listPhotoAlbumBacks(ConditionVO conditionVO) {
        return Result.success(photoAlbumService.listPhotoAlbumsAdminDTOs(conditionVO));
    }

    /**
     * 获取后台相册列表信息
     *
     * @return 相册列表信息
     */
    @Operation(summary = "获取后台相册列表信息", description = "获取后台相册列表信息")
    @GetMapping("/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.success(photoAlbumService.listPhotoAlbumAdminDTOs());
    }

    /**
     * 根据id获取后台相册信息
     *
     * @param albumId 相册id
     * @return 相册信息
     */
    @Parameter(name = "albumId", description = "相册id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据id获取后台相册信息", description = "根据id获取后台相册信息")
    @GetMapping("/info/{albumId}")
    public Result<PhotoAlbumAdminDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return Result.success(photoAlbumService.getPhotoAlbumByIdAdminDTO(albumId));
    }

    /**
     * 根据id删除相册
     *
     * @param albumId 相册id
     * @return 删除结果
     */
    @Parameter(name = "albumId", description = "相册id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据id删除相册", description = "根据id删除相册")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.success();
    }
}
