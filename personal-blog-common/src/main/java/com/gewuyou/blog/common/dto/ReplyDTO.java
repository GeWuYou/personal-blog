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
 * 回复 DTO
 *
 * @author gewuyou
 * @since 2024-05-20 下午8:02:02
 */
@Schema(description = "回复 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    /**
     * 回复 ID
     */
    @Schema(description = "回复 ID")
    private Long id;

    /**
     * 回复父 ID
     */
    @Schema(description = "回复父 ID")
    private Long parentId;

    /**
     * 用户 ID
     */
    @Schema(description = "用户 ID")
    private Long userId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户网站
     */
    @Schema(description = "用户网站")
    private String webSite;

    /**
     * 回复用户id
     */
    @Schema(description = "回复用户id")
    private Long replyUserId;

    /**
     * 回复用户昵称
     */
    @Schema(description = "回复用户昵称")
    private String replyNickname;

    /**
     * 回复用户网站
     */
    @Schema(description = "回复用户网站")
    private String replyWebsite;

    /**
     * 回复内容
     */
    @Schema(description = "回复内容")
    private String commentContent;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
