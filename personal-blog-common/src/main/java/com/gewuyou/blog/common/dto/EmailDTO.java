package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 电子邮件 DTO
 *
 * @author gewuyou
 * @since 2024-05-08 下午8:34:32
 */
@Schema(description = "电子邮件 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    /**
     * 接收者邮箱
     */
    @Schema(description = "接收者邮箱")
    private String email;

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String subject;

    /**
     * 内容映射
     */
    @Schema(description = "内容映射")
    private Map<String, Object> contentMap;

    /**
     * 模板
     */
    @Schema(description = "模板")
    private String template;
}
