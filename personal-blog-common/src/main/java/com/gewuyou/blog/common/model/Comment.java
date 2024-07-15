package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * 评论表
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_comment")
@Schema(name = "Comment对象", description = "评论表")
public class Comment extends BaseModel implements Serializable {

    @Builder
    public Comment(LocalDateTime createTime, LocalDateTime updateTime, Long id, Long replyUserId, Long userId, Long topicId, Long parentId, Byte type, Byte isDelete, Byte isReview, String commentContent) {
        super(createTime, updateTime);
        this.id = id;
        this.replyUserId = replyUserId;
        this.userId = userId;
        this.topicId = topicId;
        this.parentId = parentId;
        this.type = type;
        this.isDelete = isDelete;
        this.isReview = isReview;
        this.commentContent = commentContent;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "评论Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "回复用户id")
    @TableField("reply_user_id")
    private Long replyUserId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "评论主题id")
    @TableField("topic_id")
    private Long topicId;

    @Schema(description = "父评论id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "评论类型 1.文章 2.留言 3.关于我 4.友链 5.说说")
    @TableField("type")
    private Byte type;

    @Schema(description = "是否删除  0否 1是")
    @TableField("is_delete")
    private Byte isDelete;

    @Schema(description = "是否审核")
    @TableField("is_review")
    private Byte isReview;

    @Schema(description = "评论内容")
    @TableField("comment_content")
    private String commentContent;
}
