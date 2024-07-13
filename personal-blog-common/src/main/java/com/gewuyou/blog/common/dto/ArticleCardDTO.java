package com.gewuyou.blog.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.model.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章卡 DTO
 *
 * @author gewuyou
 * @since 2024-05-09 下午7:29:51
 */
@Schema(description = "文章卡 DTO")
@Data
public class ArticleCardDTO {
    /**
     * 文章 ID
     */
    @Schema(description = "文章 ID")
    private Long id;

    /**
     * 文章封面
     */
    @Schema(description = "文章封面")
    private String articleCover;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String articleContent;


    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶")
    private Integer isTop;
    /**
     * 是否推荐
     */
    @Schema(description = "是否推荐")
    private Integer isFeatured;

    /**
     * 文章作者
     */
    @Schema(description = "文章作者")
    private UserInfo author;

    /**
     * 文章分类名称
     */
    @Schema(description = "文章分类名称")
    private String categoryName;

    /**
     * 文章标签
     */
    @Schema(description = "文章标签")
    private List<Tag> tags;

    /**
     * 文章状态
     */
    @Schema(description = "文章状态")
    private Byte status;

    /**
     * 文章创建时间
     */
    @Schema(description = "文章创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 文章更新时间
     */
    @Schema(description = "文章更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;
}
