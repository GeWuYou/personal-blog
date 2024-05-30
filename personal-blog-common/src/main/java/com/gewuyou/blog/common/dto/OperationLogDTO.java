package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志 DTO
 *
 * @author gewuyou
 * @since 2024-05-30 下午4:31:49
 */
@Schema(description = "操作日志 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogDTO {
    /**
     * 操作日志Id
     */
    @Schema(description = "操作日志Id")
    private Integer id;

    /**
     * 操作模块
     */
    @Schema(description = "操作模块")
    private String optModule;

    /**
     * 操作URI
     */
    @Schema(description = "操作URI")
    private String optUri;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    private String optType;

    /**
     * 操作方法
     */
    @Schema(description = "操作方法")
    private String optMethod;

    /**
     * 操作描述
     */
    @Schema(description = "操作描述")
    private String optDesc;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String requestMethod;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requestParam;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private String responseData;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 操作人IP地址
     */
    @Schema(description = "操作人IP地址")
    private String ipAddress;

    /**
     * ip来源
     */
    @Schema(description = "ip来源")
    private String ipSource;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
