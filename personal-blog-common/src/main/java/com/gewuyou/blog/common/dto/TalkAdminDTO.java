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
import java.util.List;

/**
 * 说说后台DTO
 *
 * @author gewuyou
 * @since 2024-06-16 下午3:17:48
 */
@Schema(description = "说说后台DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalkAdminDTO {
    /**
     * 说说ID
     */
    @Schema(description = "说说ID")
    private Integer id;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String images;

    /**
     * 图片列表
     */
    @Schema(description = "图片列表")
    private List<String> imageList;

    /**
     * 评论数
     */
    @Schema(description = "评论数")
    private Byte isTop;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Byte status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
