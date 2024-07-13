package com.gewuyou.blog.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色VO
 *
 * @author gewuyou
 * @since 2024-05-31 下午11:48:14
 */
@Schema(description = "角色VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleVO {
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Integer id;

    /**
     * 角色名
     */
    @Schema(description = "角色名")
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    /**
     * 资源列表
     */
    @Schema(description = "资源列表")
    private List<Integer> resourceIds;

    /**
     * 菜单列表
     */
    @Schema(description = "菜单列表")
    private List<Integer> menuIds;
}
