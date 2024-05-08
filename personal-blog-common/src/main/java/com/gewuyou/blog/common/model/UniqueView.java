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
 * Unique访问统计表
 * </p>
 *
 * @author gewuyou
 * @since 2024-05-04
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "<p> Unique访问统计表 </p>")
@TableName("tb_unique_view")
public class UniqueView extends BaseModel implements Serializable {

    @Builder
    public UniqueView(LocalDateTime createTime, LocalDateTime updateTime, Integer id, Integer viewsCount) {
        super(createTime, updateTime);
        this.id = id;
        this.viewsCount = viewsCount;
    }

    @Serial
    @Schema(hidden = true)
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 访问量
     */
    @Schema(description = "访问量")
    @TableField("views_count")
    private Integer viewsCount;
}
