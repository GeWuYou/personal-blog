package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 资源DTO
 *
 * @author gewuyou
 * @since 2024-05-31 下午4:59:59
 */
@Schema(description = "资源DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
    /**
     * 资源ID
     */
    @Schema(description = "资源ID")
    private Integer id;

    /**
     * 资源名称
     */
    @Schema(description = "资源名称")
    private String resourceName;

    /**
     * 资源路径
     */
    @Schema(description = "资源路径")
    private String url;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String requestMethod;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private Byte isDisable;

    /**
     * 是否匿名
     */
    @Schema(description = "是否匿名")
    private Byte isAnonymous;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 子资源
     */
    @Schema(description = "子资源")
    private List<ResourceDTO> children;
}
