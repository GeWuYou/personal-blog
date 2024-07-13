package com.gewuyou.blog.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论后台DTO
 *
 * @author gewuyou
 * @since 2024-05-20 下午10:19:03
 */
@Schema(description = "评论后台DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentAdminDTO {
    /**
     * 评论ID
     */
    @Schema(description = "评论ID")
    private Long id;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 被回复用户的昵称
     */
    @Schema(description = "被回复用户的昵称")
    private String replyNickname;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String commentContent;

    /**
     * 评论类型
     */
    @Schema(description = "评论类型")
    private Byte type;

    /**
     * 是否审核
     */
    @Schema(description = "是否审核")
    private Byte isReview;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
