package com.gewuyou.blog.common.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    public UserInfo(LocalDateTime createTime, LocalDateTime updateTime, Long id, String nickName, String avatar, String intro, String website, Integer isSubscribe, Integer isDisable, String email) {
        super(createTime, updateTime);
        this.id = id;
        this.nickName = nickName;
        this.avatar = avatar;
        this.intro = intro;
        this.website = website;
        this.isSubscribe = isSubscribe;
        this.isDisable = isDisable;
        this.email = email;
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
    private Integer isSubscribe;

    @Schema(description = "是否禁用 0 表示否 1表示是")
    @TableField("is_disable")
    private Integer isDisable;
}
