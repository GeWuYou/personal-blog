package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 资源角色DTO
 *
 * @author gewuyou
 * @since 2024-06-01 上午12:51:15
 */
@Schema(description = "资源角色DTO")
@Data
public class ResourceRoleDTO {
    /**
     * 资源ID
     */
    @Schema(description = "资源ID")
    private Integer id;

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
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleList;

    /**
     * 是否匿名访问
     */
    @Schema(description = "是否匿名访问")
    private Byte isAnonymous;
}
