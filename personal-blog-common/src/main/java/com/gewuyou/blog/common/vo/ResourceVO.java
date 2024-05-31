package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源VO
 *
 * @author gewuyou
 * @since 2024-05-31 下午5:23:35
 */
@Schema(description = "资源VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceVO {
    /**
     * 资源id
     */
    @Schema(description = "资源id")
    private Integer id;

    /**
     * 资源名
     */
    @Schema(description = "资源名")
    @NotBlank(message = "资源名不能为空")
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
     * 父资源id
     */
    @Schema(description = "父资源id")
    private Integer parentId;

    /**
     * 是否匿名访问
     */
    @Schema(description = "是否匿名访问")
    private Byte isAnonymous;
}
