package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.ITagService;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.TagAdminDTO;
import com.gewuyou.blog.common.dto.TagOptionDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.TagVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签前端控制器
 *
 * @author gewuyou
 * @since 2024-06-01 下午11:14:11
 */
@Tag(name = "标签前端控制器", description = "标签前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/tag")
public class TagController {
    private final ITagService tagService;


    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 查询后台标签列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Operation(summary = "查询后台标签列表", description = "查询后台标签列表")
    @GetMapping("/list")
    public Result<PageResult<TagAdminDTO>> listTagsAdmin(ConditionVO conditionVO) {
        return Result.success(tagService.listTagsAdminDTOs(conditionVO));
    }

    /**
     * 搜索文章标签
     *
     * @param condition 条件
     * @return 分页结果
     */
    @Operation(summary = "搜索文章标签", description = "搜索文章标签")
    @GetMapping("/search")
    public Result<List<TagOptionDTO>> listTagsAdminBySearch(ConditionVO condition) {
        return Result.success(tagService.listTagsAdminDTOsBySearch(condition));
    }

    /**
     * 添加或修改标签
     *
     * @param tagVO 标签
     * @return 成功或失败
     */
    @Operation(summary = "添加或修改标签", description = "添加或修改标签")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    @Idempotent
    public Result<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param tagIds 标签id列表
     * @return 成功或失败
     */
    @Operation(summary = "删除标签", description = "删除标签")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    @Idempotent
    public Result<?> deleteTag(@RequestBody List<Long> tagIds) {
        tagService.deleteTag(tagIds);
        return Result.success();
    }
}
