package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 归档 DTO
 *
 * @author gewuyou
 * @since 2024-05-11 下午10:25:22
 */
@Schema(description = "归档 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveDTO {
    /**
     * 时间
     */
    @Schema(description = "时间")
    private String Time;

    /**
     * 文章卡片DTO列表
     */
    @Schema(description = "文章卡片DTO列表")
    private List<ArticleCardDTO> articles;
}
