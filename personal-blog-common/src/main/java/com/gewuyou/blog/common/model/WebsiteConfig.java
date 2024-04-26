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
 * 网站配置表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@TableName("tb_website_config")
@Schema(name = "WebsiteConfig对象", description = "网站配置表")
public class WebsiteConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "配置表Id")
    @TableId(value = "config_id", type = IdType.AUTO)
    private Long configId;

    @Schema(description = "配置表键")
    @TableField("config_key")
    private String configKey;

    @Schema(description = "配置表值")
    @TableField("config_value")
    private String configValue;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
