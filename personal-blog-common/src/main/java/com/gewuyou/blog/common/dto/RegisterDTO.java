package com.gewuyou.blog.common.dto;

import com.gewuyou.blog.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.RegularConstant.EMAIL_REGULARITY;
import static com.gewuyou.blog.common.constant.RegularConstant.USERNAME_REGULARITY;

/**
 * 注册数据传输类
 *
 * @author gewuyou
 * @since 2024-04-20 下午5:41:09
 */
@Schema(description = "注册数据传输类")
@Data
public class RegisterDTO {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @Pattern(regexp = USERNAME_REGULARITY)
    @NotEmpty(message = MessageConstant.USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(min = 6, max = 16)
    @NotEmpty(message = MessageConstant.PASSWORD_CANNOT_BE_EMPTY)
    private String password;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(regexp = EMAIL_REGULARITY)
    @NotEmpty(message = MessageConstant.MAIL_CANNOT_BE_EMPTY)
    private String email;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @Length(min = 6, max = 6)
    @NotEmpty(message = MessageConstant.VERIFICATION_CODE_CANNOT_BE_EMPTY)
    private String verifyCode;
}
