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
 * 资源表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_resource")
@Schema(name = "Resource对象", description = "资源表")
public class Resource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "资源id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "资源名称")
    @TableField("name")
    private String name;

    @Schema(description = "权限路径")
    @TableField("url")
    private String url;

    @Schema(description = "请求方法：GET, POST, PUT, DELETE等")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "父模块id")
    @TableField("parent_id")
    private Integer parentId;

    @Schema(description = "是否匿名访问 0表示否 1表示是")
    @TableField("is_anonymous")
    private Byte isAnonymous;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
