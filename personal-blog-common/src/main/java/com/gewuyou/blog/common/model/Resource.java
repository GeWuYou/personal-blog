package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-04-16
 */
@Getter
@Setter
@TableName("tb_resource")
@Schema(name = "Resource对象", description = "资源表")
public class Resource implements Serializable {

    @Schema(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "资源id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "权限路径")
    private String url;

    @Schema(description = "请求方法：GET, POST, PUT, DELETE等")
    private String requestMethod;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "是否匿名访问 0表示否 1表示是")
    private Byte isAnonymous;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}
