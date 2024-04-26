package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.MessageConstant.PASSWORD_CANNOT_BE_EMPTY;

/**
 * 重置密码视图对象
 *
 * @author gewuyou
 * @since 2024-04-20 下午7:24:18
 */
@Schema(description = "重置密码视图对象")
@Data
public class DoResetPasswordVO {
    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(min = 6, max = 16)
    @NotEmpty(message = PASSWORD_CANNOT_BE_EMPTY)
    private String password;
}
