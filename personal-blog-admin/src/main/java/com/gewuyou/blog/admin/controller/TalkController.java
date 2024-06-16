package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.ITalkService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TalkAdminDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.FilePathEnum;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.TalkVO;
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
 * <p>
 * 说说表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "<p> 说说表 前端控制器 </p>", description = "<p> 说说表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/talk")
public class TalkController {
    private final ITalkService talkService;

    private final UploadStrategyContext uploadStrategyContext;

    @Autowired
    public TalkController(
            ITalkService talkService,
            UploadStrategyContext uploadStrategyContext
    ) {
        this.talkService = talkService;
        this.uploadStrategyContext = uploadStrategyContext;
    }

    /**
     * 上传说说图片
     *
     * @param file 说说图片
     * @return 上传结果
     */
    @Parameter(name = "file", description = "说说图片", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "上传说说图片", description = "上传说说图片")
    @OperationLogging(type = OperationType.UPLOAD)
    @PostMapping("/images")
    public Result<String> saveTalkImages(MultipartFile file) {
        return Result.success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.TALK.getPath()));
    }

    /**
     * 保存或修改说说
     *
     * @param talkVO 说说
     * @return 保存或修改结果
     */
    @Operation(summary = "保存或修改说说", description = "保存或修改说说")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateTalk(@Valid @RequestBody TalkVO talkVO) {
        talkService.saveOrUpdateTalk(talkVO);
        return Result.success();
    }

    /**
     * 删除说说
     *
     * @param talkIds 说说id列表
     * @return 删除结果
     */
    @Operation(summary = "删除说说", description = "删除说说")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    public Result<?> deleteTalks(@RequestBody List<Integer> talkIds) {
        talkService.deleteTalks(talkIds);
        return Result.success();
    }

    /**
     * 查看后台说说列表
     *
     * @param conditionVO 条件
     * @return 后台说说列表
     */
    @Operation(summary = "查看后台说说列表", description = "查看后台说说列表")
    @GetMapping
    public Result<PageResultDTO<TalkAdminDTO>> listBackTalks(ConditionVO conditionVO) {
        return Result.success(talkService.listBackTalkAdminDTOs(conditionVO));
    }

    /**
     * 根据id查看后台说说详情
     *
     * @param talkId 说说id
     * @return 后台说说详情
     */
    @Parameter(name = "talkId", description = "说说id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据id查看后台说说详情", description = "根据id查看后台说说详情")
    @GetMapping("/{talkId}")
    public Result<TalkAdminDTO> getBackTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.success(talkService.getBackTalkById(talkId));
    }

}
