package com.gewuyou.blog.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gewuyou.blog.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.RegularConstant.EMAIL_REGULARITY;

/**
 * 注册数据
 *
 * @author gewuyou
 * @since 2024-04-20 下午5:41:09
 */
@Schema(description = "注册数据传输类")
@Data
public class RegisterVO {
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(regexp = EMAIL_REGULARITY)
    @NotEmpty(message = MessageConstant.MAIL_CANNOT_BE_EMPTY)
    @JsonProperty("username")
    private String email;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(min = 6, max = 16, message = "密码长度必须在6-16位之间")
    @NotEmpty(message = MessageConstant.PASSWORD_CANNOT_BE_EMPTY)
    private String password;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @Length(min = 6, max = 6, message = "验证码长度必须为6位")
    @NotEmpty(message = MessageConstant.VERIFICATION_CODE_CANNOT_BE_EMPTY)
    private String code;
}
