package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.MessageConstant.*;
import static com.gewuyou.blog.common.constant.RegularConstant.USERNAME_REGULARITY;

/**
 * 登录信息传输类
 *
 * @author gewuyou
 * @since 2024-04-17 下午10:38:54
 */
@Schema(description = "登录信息传输类")
@Data
public class LoginDTO {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @Pattern(regexp = USERNAME_REGULARITY)
    @NotEmpty(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(min = 6, max = 16)
    @NotEmpty(message = PASSWORD_CANNOT_BE_EMPTY)
    private String password;
    /**
     * 记住我
     */
    @Schema(description = "记住我")
    @NotNull(message = REMEMBER_ME_CANNOT_BE_EMPTY)
    private boolean rememberMe;
}
