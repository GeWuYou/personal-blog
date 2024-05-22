package com.gewuyou.blog.common.vo;

import com.gewuyou.blog.common.constant.MessageConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 文章访问VO
 *
 * @author gewuyou
 * @since 2024-05-11 下午8:21:41
 */
@Data
public class ArticleAccessPasswordVO {
    private Long articleId;
    @NotBlank(message = MessageConstant.ARTICLE_PASSWORD_NOT_NULL)
    private String articlePassword;
}
