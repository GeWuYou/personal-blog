package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.MessageConstant.*;

/**
 * 用户 VO
 *
 * @author gewuyou
 * @since 2024-08-17 12:02:45
 */
@Schema(description = "用户 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @NotBlank(message = EMAIL_IS_EMPTY)
    @Email(message = EMAIL_IS_INCORRECT)
    private String email;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(min = 6, max = 16, message = PASSWORD_LENGTH_ERROR)
    @NotBlank(message = PASSWORD_CANNOT_BE_EMPTY)
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
