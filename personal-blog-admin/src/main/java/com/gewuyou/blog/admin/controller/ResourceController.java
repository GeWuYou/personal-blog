package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IResourceService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.LabelOptionDTO;
import com.gewuyou.blog.common.dto.ResourceDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ResourceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "<p> 资源表 前端控制器 </p>", description = "<p> 资源表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/resource")
public class ResourceController {
    private final IResourceService resourceService;


    @Autowired
    public ResourceController(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 查看资源列表
     *
     * @param conditionVO 条件
     * @return 资源列表
     */
    @Operation(summary = "查看资源列表", description = "查看资源列表")
    @GetMapping("/list")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.success(resourceService.listResourceDTOs(conditionVO));
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     * @return 成功或失败
     */
    @Parameter(name = "resourceId", description = "资源id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "删除资源", description = "删除资源")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.success();
    }

    /**
     * 新增或修改资源
     *
     * @param resourceVO 资源VO
     * @return 成功或失败
     */
    @Operation(summary = "新增或修改资源", description = "新增或修改资源")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.success();
    }

    /**
     * 查看角色资源选项
     *
     * @return 角色资源选项
     */
    @Operation(summary = "查看角色资源选项", description = "查看角色资源选项")
    @GetMapping("/role")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.success(resourceService.listResourceOptionDTO());
    }
}
