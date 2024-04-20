package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 网站配置表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Getter
@Setter
@TableName("tb_config")
@Schema(name = "Config对象", description = "网站配置表")
public class Config implements Serializable {

    @Schema(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "配置表Id")
    @TableId(value = "config_id", type = IdType.AUTO)
    private Long configId;

    @Schema(description = "配置表键")
    private String configKey;

    @Schema(description = "配置表值")
    private String configValue;
}
