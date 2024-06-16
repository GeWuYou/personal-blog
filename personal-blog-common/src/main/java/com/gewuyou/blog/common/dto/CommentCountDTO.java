package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论计数 DTO
 *
 * @author gewuyou
 * @since 2024-06-16 下午5:04:32
 */
@Schema(description = "评论计数 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCountDTO {
    /**
     * 说说id
     */
    @Schema(description = "说说id")
    private Integer id;

    /**
     * 评论数量
     */
    @Schema(description = "评论数量")
    private Long commentCount;
}
