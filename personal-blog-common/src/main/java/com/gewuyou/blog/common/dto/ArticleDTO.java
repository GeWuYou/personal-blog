package com.gewuyou.blog.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.model.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章 DTO
 *
 * @author gewuyou
 * @since 2024-05-11 下午5:53:14
 */
@Schema(description = "文章 DTO")
@Data
@Builder
public class ArticleDTO {
    /**
     * 文章 ID
     */
    @Schema(description = "文章 ID")
    private Long id;

    /**
     * 作者信息
     */
    @Schema(description = "作者信息")
    private UserInfo author;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String categoryName;

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
    private Byte isTop;

    /**
     * 是否推荐
     */
    @Schema(description = "是否推荐")
    private Byte isFeatured;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private Byte isDelete;

    /**
     * 文章状态
     */
    @Schema(description = "文章状态")
    private Byte status;

    /**
     * 文章标签
     */
    @Schema(description = "文章标签")
    private List<Tag> tags;

    /**
     * 文章浏览量
     */
    @Schema(description = "文章浏览量")
    private Long viewCount;

    /**
     * 文章类型
     */
    @Schema(description = "文章类型")
    private Byte type;

    /**
     * 文章来源地址
     */
    @Schema(description = "文章来源地址")
    private String originalUrl;

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

    /**
     * 上一篇文章卡片DTO
     */
    @Schema(description = "上一篇文章卡片DTO")
    private ArticleCardDTO preArticleCard;

    /**
     * 下一篇文章DTO
     */
    @Schema(description = "下一篇文章DTO")
    private ArticleCardDTO nextArticleCard;
}
