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

/**
 * 友链后台DTO
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:31:43
 */
@Schema(description = "友链后台DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkAdminDTO {


    /**
     * 友链 id
     */
    @Schema(description = "友链 id")
    private Integer id;

    /**
     * 友链名称
     */
    @Schema(description = "友链名称")
    private String linkName;

    /**
     * 友链头像
     */
    @Schema(description = "友链头像")
    private String linkAvatar;

    /**
     * 友链地址
     */
    @Schema(description = "友链地址")
    private String linkAddress;

    /**
     * 友链介绍
     */
    @Schema(description = "友链介绍")
    private String linkIntro;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
