package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.gewuyou.blog.common.constant.MessageConstant.*;

/**
 * 管理员重置密码视图对象
 *
 * @author gewuyou
 * @since 2024-04-20 下午7:24:18
 */
@Schema(description = "管理员重置密码视图对象")
@Data
public class AdminPasswordVO {
    /**
     * 旧密码
     */
    @Schema(description = "旧密码")
    @NotBlank(message = OLD_PASSWORD_CANNOT_BE_EMPTY)
    private String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @Length(min = 6, max = 16, message = NEW_PASSWORD_LENGTH_ERROR)
    @NotBlank(message = NEW_PASSWORD_CANNOT_BE_EMPTY)
    private String newPassword;
}
