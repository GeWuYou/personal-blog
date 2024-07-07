package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.vo.EmailVO;
import com.gewuyou.blog.common.vo.SubscribeVO;
import com.gewuyou.blog.common.vo.UserInfoVO;
import com.gewuyou.blog.server.service.IUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/select/{id}")
    public UserInfo selectUserInfoById(@PathVariable("id") Long id) {
        log.info("selectUserInfoById, id: {}", id);
        return userInfoService.selectUserInfoById(id);
    }

    /**
     * 插入用户信息
     *
     * @param userInfo 用户信息
     */
    @PostMapping("/insert")
    public void userInfoInsert(UserInfo userInfo) {
        log.info("userInfoInsert, userInfo: {}", userInfo);
        userInfoService.insert(userInfo);
    }

    /**
     * 获取用户数量
     * @return 用户数量
     */
    @GetMapping("/count")
    public Result<Long> selectCount() {
        return Result.success(userInfoService.selectCount());
    }


    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return 更新结果
     */
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.success();
    }

    /**
     * 更新用户头像
     *
     * @param file 用户头像
     * @return 更新结果
     */
    @OperationLogging(type = OperationType.UPDATE)
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.success(userInfoService.updateUserAvatar(file));
    }

    /**
     * 绑定邮箱
     *
     * @param emailVO 邮箱信息
     * @return 绑定结果
     */
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/email")
    public Result<?> saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return Result.success();
    }

    /**
     * 更新订阅状态
     *
     * @param subscribeVO 订阅信息
     * @return 更新结果
     */
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/subscribe")
    public Result<?> updateUserSubscribe(@RequestBody SubscribeVO subscribeVO) {
        userInfoService.updateUserSubscribe(subscribeVO);
        return Result.success();
    }

    /**
     * 根据用户Id查询用户信息
     *
     * @param userInfoId 用户Id
     * @return 用户信息
     */
    @GetMapping("/{userInfoId}")
    public Result<UserInfoDTO> getUserInfoById(@PathVariable("userInfoId") Long userInfoId) {
        return Result.success(userInfoService.getUserInfoById(userInfoId));
    }

}
