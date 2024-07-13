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
 * 菜单表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_menu")
@Schema(name = "Menu对象", description = "菜单表")
public class Menu extends BaseModel implements Serializable {
    @Builder
    public Menu(LocalDateTime createTime, LocalDateTime updateTime, Integer id, String name, String path, String component, String icon, Integer orderNum, Integer parentId, Byte isHidden) {
        super(createTime, updateTime);
        this.id = id;
        this.name = name;
        this.path = path;
        this.component = component;
        this.icon = icon;
        this.orderNum = orderNum;
        this.parentId = parentId;
        this.isHidden = isHidden;
    }


    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "菜单名")
    @TableField("name")
    private String name;

    @Schema(description = "菜单路径")
    @TableField("path")
    private String path;

    @Schema(description = "菜单组件")
    @TableField("component")
    private String component;

    @Schema(description = "菜单图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "排序号")
    @TableField("order_num")
    private Integer orderNum;

    @Schema(description = "父菜单id(默认为0，表示顶级菜单)")
    @TableField("parent_id")
    private Integer parentId;

    @Schema(description = "是否隐藏 0表示否。1表示是")
    @TableField("is_hidden")
    private Byte isHidden;
}
