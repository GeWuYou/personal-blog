package com.gewuyou.blog.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 任务日志DTO
 *
 * @author gewuyou
 * @since 2024-05-30 下午2:52:58
 */
@Schema(description = "任务日志DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLogDTO {
    /**
     * 任务日志ID
     */
    @Schema(description = "任务日志ID")
    private Integer id;

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Integer jobId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String jobName;

    /**
     * 任务分组
     */
    @Schema(description = "任务分组")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @Schema(description = "调用目标字符串")
    private String invokeTarget;

    /**
     * 任务消息
     */
    @Schema(description = "任务消息")
    private String jobMessage;

    /**
     * 执行状态
     */
    @Schema(description = "执行状态")
    private Byte status;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date endTime;
}
