package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.TagDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.server.service.ITagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签表 前端控制器
 *
 * @author gewuyou
 * @since 2024-04-23
 */

@io.swagger.v3.oas.annotations.tags.Tag(name = "标签表 前端控制器", description = "标签表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/tag")
public class TagController {

    private final ITagService tagService;

    @Autowired
    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 获取标签总数
     *
     * @return Long
     */
    @Operation(summary = "获取标签总数", description = "获取标签总数")
    @GetMapping("/count")
    public Result<Long> selectCount() {
        return Result.success(tagService.selectCount());
    }

    /**
     * 获取所有标签
     *
     * @return List<TagDTO>
     */
    @Operation(summary = "获取所有标签", description = "获取所有标签")
    @GetMapping("/list")
    public Result<List<TagDTO>> getAllTags() {
        return Result.success(tagService.listTagDTOs());
    }

    /**
     * 获取所有标签
     *
     * @return List<Tag>
     */
    @Operation(summary = "获取所有标签", description = "获取所有标签")
    @GetMapping("/all")
    public Result<List<Tag>> listTags() {
        return Result.success(tagService.listTags());
    }


    /**
     * 获取前10个标签
     *
     * @return List<TagDTO>
     */
    @Operation(summary = "获取前10个标签", description = "获取前10个标签")
    @GetMapping("/topTen")
    public Result<List<TagDTO>> getTopTenTags() {
        return Result.success(tagService.listTenTagDTOs());
    }
}
