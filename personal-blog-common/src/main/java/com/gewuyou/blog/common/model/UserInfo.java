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
 * 用户信息表
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_info")
@Schema(name = "UserInfo对象", description = "用户信息表")
public class UserInfo extends BaseModel implements Serializable {
    @Builder
    public UserInfo(LocalDateTime createTime, LocalDateTime updateTime, Long id, String nickName, String email, String avatar, String intro, String website, Byte isSubscribe, Byte isDisable) {
        super(createTime, updateTime);
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.avatar = avatar;
        this.intro = intro;
        this.website = website;
        this.isSubscribe = isSubscribe;
        this.isDisable = isDisable;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "用户头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "用户简介")
    @TableField("intro")
    private String intro;

    @Schema(description = "个人网站")
    @TableField("website")
    private String website;

    @Schema(description = "是否订阅")
    @TableField("is_subscribe")
    private Byte isSubscribe;

    @Schema(description = "是否禁用 0 表示否 1表示是")
    @TableField("is_disable")
    private Byte isDisable;
}
