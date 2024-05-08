package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_website_config")
@Schema(name = "WebsiteConfig对象", description = "网站配置表")
public class WebsiteConfig extends BaseModel implements Serializable {

    @Builder
    public WebsiteConfig(LocalDateTime createTime, LocalDateTime updateTime, Long id, String config) {
        super(createTime, updateTime);
        this.id = id;
        this.config = config;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "配置表Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "配置信息")
    @TableField("config")
    private String config;
}
