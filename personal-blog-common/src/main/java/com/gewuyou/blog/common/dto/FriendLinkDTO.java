package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友情链接 DTO
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:16:57
 */
@Schema(description = "友情链接 DTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkDTO {
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
}
