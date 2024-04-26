package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_comment")
@Schema(name = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "评论Id")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @Schema(description = "文章Id")
    @TableField("article_id")
    private Long articleId;

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
    @TableField("parent_comment_id")
    private Long parentCommentId;

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
    @TableField("content")
    private String content;

    @Schema(description = "评论时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
