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
 * 操作日志表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Getter
@Setter
@TableName("tb_operation_log")
@Schema(name = "OperationLog对象", description = "操作日志表")
public class OperationLog implements Serializable {

    @Schema(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "日志主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "操作模块")
    private String optModule;

    @Schema(description = "操作路径")
    private String optUrl;

    @Schema(description = "操作类型")
    private String optType;

    @Schema(description = "操作方法")
    private String optMethod;

    @Schema(description = "操作描述")
    private String optDesc;

    @Schema(description = "请求方法")
    private String requestMethod;

    @Schema(description = "请求参数")
    private String requestParam;

    @Schema(description = "响应数据")
    private String responseData;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "用户登录ip")
    private String ipAddress;

    @Schema(description = "ip属地")
    private String ipSource;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}
