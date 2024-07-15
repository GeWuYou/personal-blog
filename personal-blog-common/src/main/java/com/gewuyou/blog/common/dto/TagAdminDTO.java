package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标签后台DTO
 *
 * @author gewuyou
 * @since 2024-06-02 下午1:52:02
 */
@Schema(description = "标签后台DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagAdminDTO {
    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long id;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String tagName;

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Long articleCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

}
