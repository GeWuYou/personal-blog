package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 异常日志表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_exception_log")
@Schema(name = "ExceptionLog对象", description = "异常日志表	")
public class ExceptionLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "请求接口")
    @TableField("opt_uri")
    private String optUri;

    @Schema(description = "请求方式")
    @TableField("opt_method")
    private String optMethod;

    @Schema(description = "请求方式")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "请求参数")
    @TableField("request_param")
    private String requestParam;

    @Schema(description = "操作描述")
    @TableField("opt_desc")
    private String optDesc;

    @Schema(description = "异常信息")
    @TableField("exception_info")
    private String exceptionInfo;

    @Schema(description = "ip")
    @TableField("ip_address")
    private String ipAddress;

    @Schema(description = "ip属地")
    @TableField("ip_source")
    private String ipSource;

    @Schema(description = "操作时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
