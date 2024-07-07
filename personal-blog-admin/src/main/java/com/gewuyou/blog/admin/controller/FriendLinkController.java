package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IFriendLinkService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.FriendLinkAdminDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.FriendLinkVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 友链前端控制器
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:11:28
 */
@Tag(name = "友链前端控制器", description = "友链前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/link")
public class FriendLinkController {
    private final IFriendLinkService friendLinkService;


    @Autowired
    public FriendLinkController(IFriendLinkService friendLinkService) {
        this.friendLinkService = friendLinkService;
    }

    /**
     * 查看后台友链列表
     *
     * @param conditionVO 条件
     * @return 友链列表
     */
    @Operation(summary = "查看后台友链列表", description = "查看后台友链列表")
    @GetMapping("/list")
    public Result<PageResultDTO<FriendLinkAdminDTO>> listFriendLinkDTO(ConditionVO conditionVO) {
        return Result.success(friendLinkService.listFriendLinksAdminDTOs(conditionVO));
    }

    /**
     * 保存或更新友链
     *
     * @param friendLinkVO 友链VO
     * @return 成功或失败
     */
    @Operation(summary = "保存或更新友链", description = "保存或更新友链")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVO friendLinkVO) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVO);
        return Result.success();
    }

    /**
     * 删除友链
     *
     * @param linkIdList 友链ID列表
     * @return 成功或失败
     */
    @Operation(summary = "删除友链", description = "删除友链")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    public Result<?> deleteFriendLink(@RequestBody List<Integer> linkIdList) {
        friendLinkService.removeByIds(linkIdList);
        return Result.success();
    }
}
