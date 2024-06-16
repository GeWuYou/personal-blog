package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 说说DTO
 *
 * @author gewuyou
 * @since 2024-06-16 下午4:27:57
 */
@Schema(description = "说说DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalkDTO {
    /**
     * 说说id
     */
    @Schema(description = "说说id")
    private Integer id;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String images;

    /**
     * 图片列表
     */
    @Schema(description = "图片列表")
    private List<String> imageList;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶")
    private Byte isTop;

    /**
     * 评论数
     */
    @Schema(description = "评论数")
    private Long commentCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
