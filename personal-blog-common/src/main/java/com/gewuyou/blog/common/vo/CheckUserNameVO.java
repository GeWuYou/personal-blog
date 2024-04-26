package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.gewuyou.blog.common.constant.MessageConstant.USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.RegularConstant.USERNAME_REGULARITY;

/**
 * 检查用户名功能视图对象
 *
 * @author gewuyou
 * @since 2024-04-20 下午8:16:20
 */
@Schema(description = "检查用户名功能视图对象")
@Data
public class CheckUserNameVO {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @Pattern(regexp = USERNAME_REGULARITY)
    @NotEmpty(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
    private String username;
}
