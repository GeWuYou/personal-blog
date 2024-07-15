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
 * 标签表
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_tag")
@Schema(name = "Tag对象", description = "标签表")
public class Tag extends BaseModel implements Serializable {
    @Builder
    public Tag(LocalDateTime createTime, LocalDateTime updateTime, Long id, String tagName) {
        super(createTime, updateTime);
        this.id = id;
        this.tagName = tagName;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "标签Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "标签名")
    @TableField("tag_name")
    private String tagName;
}
