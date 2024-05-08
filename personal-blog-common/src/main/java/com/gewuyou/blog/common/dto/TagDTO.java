package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标记 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午10:30:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "标记 DTO")
public class TagDTO {
    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String tagName;

    /**
     * 标签数量
     */
    @Schema(description = "标签数量")
    private Integer count;

}
