package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.server.service.IUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "<p> 用户信息表 前端控制器 </p>", description = "<p> 用户信息表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/user-info")
@Slf4j
public class UserInfoController {
    private final IUserInfoService userInfoService;

    @Autowired
    public UserInfoController(
            IUserInfoService userInfoService
    ) {
        this.userInfoService = userInfoService;
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    @Parameter(name = "id", description = "用户id", in = ParameterIn.PATH, required = true)
    @Operation(summary = "根据id查询用户信息", description = "根据id查询用户信息")
    @GetMapping("/{id}")
    public UserInfo selectUserInfoById(@PathVariable("id") Long id) {
        log.info("selectUserInfoById, id: {}", id);
        return userInfoService.selectUserInfoById(id);
    }

    @Operation(summary = "", description = "")
    @PostMapping("/insert")
    public void userInfoInsert(UserInfo userInfo) {
        log.info("userInfoInsert, userInfo: {}", userInfo);
        userInfoService.insert(userInfo);
    }

    @Operation(summary = "", description = "")
    @GetMapping("/count")
    public Long selectCount() {
        return userInfoService.selectCount();
    }
}
