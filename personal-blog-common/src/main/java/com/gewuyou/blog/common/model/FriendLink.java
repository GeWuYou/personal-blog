package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 友链表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_friend_link")
@Schema(name = "FriendLink对象", description = "友链表")
public class FriendLink extends BaseModel implements Serializable {

    @Builder
    public FriendLink(LocalDateTime createTime, LocalDateTime updateTime, Integer id, String linkName, String linkAvatar, String linkAddress, String linkIntro) {
        super(createTime, updateTime);
        this.id = id;
        this.linkName = linkName;
        this.linkAvatar = linkAvatar;
        this.linkAddress = linkAddress;
        this.linkIntro = linkIntro;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "链接名")
    @TableField("link_name")
    private String linkName;

    @Schema(description = "链接头像")
    @TableField("link_avatar")
    private String linkAvatar;

    @Schema(description = "链接地址")
    @TableField("link_address")
    private String linkAddress;

    @Schema(description = "链接介绍")
    @TableField("link_intro")
    private String linkIntro;
}
