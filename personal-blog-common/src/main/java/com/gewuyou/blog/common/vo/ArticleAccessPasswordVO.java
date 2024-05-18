package com.gewuyou.blog.common.vo;

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
    private String articlePassword;
}
