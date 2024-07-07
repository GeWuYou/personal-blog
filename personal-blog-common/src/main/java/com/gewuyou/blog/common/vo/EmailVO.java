package com.gewuyou.blog.common.vo;

import com.gewuyou.blog.common.constant.MessageConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电子邮件 VO
 *
 * @author gewuyou
 * @since 2024-07-06 上午11:44:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailVO {
    /**
     * 邮箱
     */
    @NotBlank(message = MessageConstant.EMAIL_IS_EMPTY)
    @Email(message = MessageConstant.EMAIL_IS_INCORRECT)
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = MessageConstant.VERIFICATION_CODE_CANNOT_BE_EMPTY)
    private String code;
}
