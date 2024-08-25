package com.gewuyou.blog.common.vo;

import com.gewuyou.blog.common.constant.MessageConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友链接VO
 *
 * @author gewuyou
 * @since 2024-05-22 下午8:46:01
 */
@Schema(description = "好友链接VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkVO {
    /**
     * 友链id
     */
    @Schema(description = "友链id")
    private Integer id;


    /**
     * 友链名称
     */
    @Schema(description = "友链名称")
    @NotBlank(message = MessageConstant.LINK_NAME_NOT_EMPTY)
    private String linkName;

    /**
     * 友链头像
     */
    @Schema(description = "友链头像")
    @NotBlank(message = MessageConstant.LINK_AVATAR_NOT_EMPTY)
    private String linkAvatar;

    /**
     * 友链地址
     */
    @Schema(description = "友链地址")
    @NotBlank(message = MessageConstant.LINK_ADDRESS_NOT_EMPTY)
    private String linkAddress;

    /**
     * 友链介绍
     */
    @Schema(description = "友链介绍")
    @NotBlank(message = MessageConstant.LINK_INTRO_NOT_EMPTY)
    private String linkIntro;
}
