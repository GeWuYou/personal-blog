package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 异常日志 DTO
 *
 * @author gewuyou
 * @since 2024-05-22 下午6:49:57
 */
@Schema(description = "异常日志 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLogDTO {
    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Integer id;

    /**
     * 操作url
     */
    @Schema(description = "操作url")
    private String optUri;

    /**
     * 操作方法
     */
    @Schema(description = "操作方法")
    private String optMethod;

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
     * 操作描述
     */
    @Schema(description = "操作描述")
    private String optDesc;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exceptionInfo;

    /**
     * ip地址
     */
    @Schema(description = "ip地址")
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
    private Date createTime;
}
