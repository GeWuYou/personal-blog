package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TalkDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.ITalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 说说表 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "说说表 前端控制器", description = "说说表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/talk")
public class TalkController {
    private final ITalkService talkService;


    @Autowired
    public TalkController(
            ITalkService talkService
    ) {
        this.talkService = talkService;
    }

    /**
     * 查看说说列表
     *
     * @return 说说列表
     */
    @Operation(summary = "查看说说列表", description = "查看说说列表")
    @GetMapping("/list")
    public Result<PageResultDTO<TalkDTO>> listTalks() {
        return Result.success(talkService.listTalkDTOs());
    }


    /**
     * 根据id查看说说详情
     *
     * @param talkId 说说id
     * @return 说说详情
     */
    @Parameter(name = "talkId", description = "说说id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据id查看说说详情", description = "根据id查看说说详情")
    @GetMapping("/{talkId}")
    public Result<TalkDTO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.success(talkService.getTalkById(talkId));
    }
    @RequestMapping("/count")
    public Long selectCount() {
        return talkService.selectCount();
    }
}
