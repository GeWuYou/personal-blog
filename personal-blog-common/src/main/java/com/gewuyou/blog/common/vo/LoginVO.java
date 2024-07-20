package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.MessageConstant.PASSWORD_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.MessageConstant.USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY;

/**
 * 登录信息视图对象
 *
 * @author gewuyou
 * @since 2024-04-17 下午10:38:54
 */
@Schema(description = "登录信息视图对象")
@Data
public class LoginVO {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotEmpty(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(min = 6, max = 16, message = "密码长度必须在6到16位之间")
    @NotEmpty(message = PASSWORD_CANNOT_BE_EMPTY)
    private String password;
}
