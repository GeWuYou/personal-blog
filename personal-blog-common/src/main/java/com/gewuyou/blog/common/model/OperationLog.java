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
 * 操作日志表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_operation_log")
@Schema(name = "OperationLog对象", description = "操作日志表")
public class OperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "日志主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "操作模块")
    @TableField("opt_module")
    private String optModule;

    @Schema(description = "操作路径")
    @TableField("opt_url")
    private String optUrl;

    @Schema(description = "操作类型")
    @TableField("opt_type")
    private String optType;

    @Schema(description = "操作方法")
    @TableField("opt_method")
    private String optMethod;

    @Schema(description = "操作描述")
    @TableField("opt_desc")
    private String optDesc;

    @Schema(description = "请求方法")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "请求参数")
    @TableField("request_param")
    private String requestParam;

    @Schema(description = "响应数据")
    @TableField("response_data")
    private String responseData;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "用户名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "用户登录ip")
    @TableField("ip_address")
    private String ipAddress;

    @Schema(description = "ip属地")
    @TableField("ip_source")
    private String ipSource;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
