package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 评论 DTO
 *
 * @author gewuyou
 * @since 2024-05-20 下午8:00:28
 */
@Schema(description = "评论 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    /**
     * 评论ID
     */
    @Schema(description = "评论ID")
    private Long id;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户网站
     */
    @Schema(description = "用户网站")
    private String webSite;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String commentContent;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 回复DTO列表
     */
    @Schema(description = "回复DTO列表")
    private List<ReplyDTO> replyDTOs;
}
