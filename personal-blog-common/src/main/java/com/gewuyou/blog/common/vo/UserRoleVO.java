package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户角色 VO
 *
 * @author gewuyou
 * @since 2024-07-04 下午9:06:07
 */
@Schema(description = "用户角色 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVO {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotNull(message = "id不能为空")
    private Long userInfoId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 角色id列表
     */
    @Schema(description = "角色id列表")
    @NotNull(message = "用户角色不能为空")
    private List<Integer> roleIds;
}
