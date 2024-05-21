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
 * <p>
 * 说说表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_talk")
@Schema(name = "Talk对象", description = "说说表")
public class Talk extends BaseModel implements Serializable {
    @Builder
    public Talk(LocalDateTime createTime, LocalDateTime updateTime, Integer id, Long userId, String content, String images, Byte isTop, Byte status) {
        super(createTime, updateTime);
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.images = images;
        this.isTop = isTop;
        this.status = status;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "说说id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "说说内容")
    @TableField("content")
    private String content;

    @Schema(description = "图片")
    @TableField("images")
    private String images;

    @Schema(description = "是否置顶")
    @TableField("is_top")
    private Byte isTop;

    @Schema(description = "状态 1.公开 2.私密")
    @TableField("status")
    private Byte status;
}
