package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.MessageConstant.MAIL_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.MessageConstant.VERIFICATION_CODE_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.RegularConstant.EMAIL_REGULARITY;

/**
 * 开始重置密码数据传输类
 *
 * @author gewuyou
 * @since 2024-04-20 下午7:07:23
 */
@Schema(description = "开始重置密码数据传输类")
@Data
public class StartResetPasswordDTO {
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(regexp = EMAIL_REGULARITY)
    @NotEmpty(message = MAIL_CANNOT_BE_EMPTY)
    private String email;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @Length(min = 6, max = 6)
    @NotEmpty(message = VERIFICATION_CODE_CANNOT_BE_EMPTY)
    private String verifyCode;
}
