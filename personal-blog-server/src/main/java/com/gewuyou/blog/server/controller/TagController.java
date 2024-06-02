package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.TagDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.server.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */

@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/tag")
public class TagController {

    private final ITagService tagService;

    @Autowired
    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/count")
    public Long selectCount() {
        return tagService.selectCount();
    }

    /**
     * 获取所有标签
     *
     * @return List<TagDTO>
     */
    @GetMapping("/list")
    public Result<List<TagDTO>> getAllTags() {
        return Result.success(tagService.listTagDTOs());
    }

    /**
     * 获取所有标签
     *
     * @return List<Tag>
     */
    @GetMapping("/all")
    public Result<List<Tag>> listTags() {
        return Result.success(tagService.listTags());
    }


    /**
     * 获取前10个标签
     *
     * @return List<TagDTO>
     */
    @GetMapping("/topTen")
    public Result<List<TagDTO>> getTopTenTags() {
        return Result.success(tagService.listTenTagDTOs());
    }
}
