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
 * 标签表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_tag")
@Schema(name = "Tag对象", description = "标签表")
public class Tag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "标签Id")
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Long tagId;

    @Schema(description = "标签名")
    @TableField("tag_name")
    private String tagName;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
