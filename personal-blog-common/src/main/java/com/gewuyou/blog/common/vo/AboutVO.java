package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关于VO
 *
 * @author gewuyou
 * @since 2024-05-05 下午8:47:36
 */
@Schema(description = "关于VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutVO {
    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;
}
