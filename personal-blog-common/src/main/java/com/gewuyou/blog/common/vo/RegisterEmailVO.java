package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import static com.gewuyou.blog.common.constant.MessageConstant.MAIL_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.RegularConstant.EMAIL_REGULARITY;

/**
 * 注册电子邮件
 *
 * @author gewuyou
 * @since 2024-04-20 下午2:53:38
 */
@Schema(description = "注册电子邮件数据传输类")
@Data
public class RegisterEmailVO {
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(regexp = EMAIL_REGULARITY)
    @NotEmpty(message = MAIL_CANNOT_BE_EMPTY)
    private String email;
}
