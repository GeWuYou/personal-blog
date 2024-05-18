package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 文章搜索 DTO
 *
 * @author gewuyou
 * @since 2024-05-13 下午8:36:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article")
public class EsArticleDTO {
    /**
     * 文章 ID
     */
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleContent;

    @Field(type = FieldType.Byte)
    private Byte isDelete;

    @Field(type = FieldType.Byte)
    private Byte status;
}
