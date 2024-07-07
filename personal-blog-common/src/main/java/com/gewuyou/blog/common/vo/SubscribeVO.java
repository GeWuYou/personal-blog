package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订阅 VO
 *
 * @author gewuyou
 * @since 2024-07-06 上午11:59:23
 */
@Schema(description = "订阅 VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeVO {
    /**
     * 用户信息 ID
     */
    @Schema(description = "用户信息 ID")
    private Long userInfoId;

    /**
     * 是否订阅
     */
    @Schema(description = "是否订阅")
    private Byte isSubscribe;
}
