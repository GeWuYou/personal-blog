package com.gewuyou.blog.common.vo;

import com.gewuyou.blog.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gewuyou.blog.common.constant.MessageConstant.COMMENT_CANNOT_BE_EMPTY;

/**
 * 评论VO
 *
 * @author gewuyou
 * @since 2024-05-19 下午7:08:13
 */
@Schema(description = "评论VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    /**
     * 被回复的用户id
     */
    @Schema(description = "被回复的用户id")
    private Long replyUserId;

    /**
     * 主题id
     */
    @Schema(description = "主题id")
    private Long topicId;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    @NotBlank(message = COMMENT_CANNOT_BE_EMPTY)
    private String commentContent;

    /**
     * 父评论id
     */
    @Schema(description = "父评论id")
    private Long parentId;

    /**
     * 评论类型
     */
    @Schema(description = "评论类型")
    @NotEmpty(message = MessageConstant.COMMENT_TYPE_CANNOT_BE_EMPTY)
    private Byte type;
}
