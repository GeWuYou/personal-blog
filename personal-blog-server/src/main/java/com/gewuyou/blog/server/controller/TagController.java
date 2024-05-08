package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.server.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/count")
    public Long selectCount() {
        return tagService.selectCount();
    }

    @RequestMapping("/list")
    public List<Tag> listTags() {
        return tagService.listTags();
    }
}
