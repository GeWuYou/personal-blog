package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QQ用户信息DTO
 *
 * @author gewuyou
 * @since 2024-07-18 下午12:17:16
 */
@Schema(description = "QQ用户信息DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QQUserInfoDTO {
    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像地址（50×50像素）
     */
    @Schema(description = "用户头像地址（50×50像素）")
    private String figureurl_qq_1;
}
