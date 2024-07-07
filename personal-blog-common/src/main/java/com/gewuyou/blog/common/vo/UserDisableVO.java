package com.gewuyou.blog.common.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户禁用 VO
 *
 * @author gewuyou
 * @since 2024-07-04 下午9:45:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDisableVO {
    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotNull(message = "用户禁用状态不能为空")
    private Byte isDisable;
}
