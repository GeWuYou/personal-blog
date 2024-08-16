package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IPhotoService;
import com.gewuyou.blog.admin.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.PhotoAdminDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import com.gewuyou.blog.common.vo.PhotoInfoVO;
import com.gewuyou.blog.common.vo.PhotoVO;
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
 * 照片前端控制器
 *
 * @author gewuyou
 * @since 2024-05-31 下午1:16:29
 */
@Tag(name = "照片前端控制器", description = "照片前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/photo")
public class PhotoController {

    private final IPhotoService photoService;

    private final UploadStrategyContext uploadStrategyContext;

    @Autowired
    public PhotoController(IPhotoService photoService, UploadStrategyContext uploadStrategyContext) {
        this.photoService = photoService;
        this.uploadStrategyContext = uploadStrategyContext;
    }

    /**
     * 上传照片
     *
     * @param file 照片文件
     * @return 上传结果
     */
    @Parameter(name = "file", description = "照片文件", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "上传照片", description = "上传照片")
    @OperationLogging(type = OperationType.UPLOAD, logParams = false)
    @PostMapping("/upload")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.success(uploadStrategyContext
                .executeUploadStrategy(file, FilePathEnum.PHOTO.getPath())
                .join());
    }

    /**
     * 获取照片列表
     *
     * @param conditionVO 条件
     * @return 照片列表
     */
    @Operation(summary = "获取照片列表", description = "获取照片列表")
    @GetMapping("/list")
    public Result<PageResultDTO<PhotoAdminDTO>> listPhotos(ConditionVO conditionVO) {
        return Result.success(photoService.listPhotoAdminDTOs(conditionVO));
    }

    /**
     * 更新照片信息
     *
     * @param photoInfoVO 照片信息
     * @return 更新结果
     */
    @Operation(summary = "更新照片信息", description = "更新照片信息")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping
    public Result<?> updatePhoto(@Valid @RequestBody PhotoInfoVO photoInfoVO) {
        photoService.updatePhoto(photoInfoVO);
        return Result.success();
    }

    /**
     * 保存照片
     *
     * @param photoVO 照片VO
     * @return 保存结果
     */
    @Operation(summary = "保存照片", description = "保存照片")
    @OperationLogging(type = OperationType.SAVE)
    @PostMapping
    @Idempotent
    public Result<?> savePhotos(@Valid @RequestBody PhotoVO photoVO) {
        photoService.savePhotos(photoVO);
        return Result.success();
    }

    /**
     * 移动照片到指定相册
     *
     * @param photoVO 照片VO
     * @return 移动结果
     */
    @Operation(summary = "移动照片到指定相册", description = "移动照片到指定相册")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/album")
    public Result<?> updatePhotosAlbum(@Valid @RequestBody PhotoVO photoVO) {
        photoService.updatePhotosAlbum(photoVO);
        return Result.success();
    }

    /**
     * 更新照片删除状态
     *
     * @param deleteVO 删除VO
     * @return 更新结果
     */
    @Operation(summary = "更新照片删除状态", description = "更新照片删除状态")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/delete")
    @Idempotent
    public Result<?> updatePhotoDelete(@Valid @RequestBody DeleteVO deleteVO) {
        photoService.updatePhotoDelete(deleteVO);
        return Result.success();
    }

    /**
     * 删除照片
     *
     * @param photoIds 照片ID列表
     * @return 删除结果
     */
    @Operation(summary = "删除照片", description = "删除照片")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    @Idempotent
    public Result<?> deletePhotos(@RequestBody List<Integer> photoIds) {
        photoService.deletePhotos(photoIds);
        return Result.success();
    }
}
