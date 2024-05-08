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
 * 资源表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_resource")
@Schema(name = "Resource对象", description = "资源表")
public class Resource extends BaseModel implements Serializable {

    @Builder
    public Resource(LocalDateTime createTime, LocalDateTime updateTime, Integer id, String name, String url, String requestMethod, Integer parentId, Byte isAnonymous) {
        super(createTime, updateTime);
        this.id = id;
        this.name = name;
        this.url = url;
        this.requestMethod = requestMethod;
        this.parentId = parentId;
        this.isAnonymous = isAnonymous;
    }

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
}
