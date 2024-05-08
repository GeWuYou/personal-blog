package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网站配置 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午6:09:25
 */
@Schema(description = "网站配置 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteConfigDTO {
    /**
     * 网站名称
     */
    @Schema(description = "网站名称")
    private String name;

    /**
     * 网站英文名称
     */
    @Schema(description = "网站英文名称")
    private String englishName;

    /**
     * 作者
     */
    @Schema(description = "作者")
    private String author;

    /**
     * 作者头像
     */
    @Schema(description = "作者头像")
    private String authorAvatar;

    /**
     * 作者简介
     */
    @Schema(description = "作者简介")
    private String authorIntro;

    /**
     * logo
     */
    @Schema(description = "logo")
    private String logo;

    /**
     * 多语言
     */
    @Schema(description = "多语言")
    private Integer multiLanguage;

    /**
     * 通知
     */
    @Schema(description = "通知")
    private String notice;

    /**
     * 网站创建时间
     */
    @Schema(description = "网站创建时间")
    private String websiteCreateTime;

    /**
     * 备案号
     */
    @Schema(description = "备案号")
    private String beianNumber;

    /**
     * qq登录
     */
    @Schema(description = "qq登录")
    private Integer qqLogin;

    /**
     * github登录
     */
    @Schema(description = "github登录")
    private String github;

    /**
     * gitee登录
     */
    @Schema(description = "gitee登录")
    private String gitee;

    /**
     * qq
     */
    @Schema(description = "qq")
    private String qq;

    /**
     * 微信
     */
    @Schema(description = "微信")
    private String weChat;

    /**
     * 微博
     */
    @Schema(description = "微博")
    private String weibo;

    /**
     * csdn
     */
    @Schema(description = "csdn")
    private String csdn;

    /**
     * 知乎
     */
    @Schema(description = "知乎")
    private String zhihu;

    /**
     * 掘金
     */
    @Schema(description = "掘金")
    private String juejin;

    /**
     * 推特
     */
    @Schema(description = "推特")
    private String twitter;

    /**
     * stackoverflow
     */
    @Schema(description = "stackoverflow")
    private String stackoverflow;

    /**
     * 游客头像
     */
    @Schema(description = "游客头像")
    private String touristAvatar;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userAvatar;

    /**
     * 是否开启评论审核
     */
    @Schema(description = "是否开启评论审核")
    private Integer isCommentReview;

    /**
     * 是否开启邮件通知
     */
    @Schema(description = "是否开启邮件通知")
    private Integer isEmailNotice;

    /**
     * 是否开启文章打赏
     */
    @Schema(description = "是否开启文章打赏")
    private Integer isReward;

    /**
     * 微信二维码
     */
    @Schema(description = "微信二维码")
    private String weiXinQRCode;

    /**
     * 支付宝二维码
     */
    @Schema(description = "支付宝二维码")
    private String alipayQRCode;

    /**
     * 网站图标
     */
    @Schema(description = "网站图标")
    private String favicon;

    /**
     * 网页标题
     */
    @Schema(description = "网页标题")
    private String websiteTitle;

    /**
     * 公安部备案编号
     */
    @Schema(description = "公安部备案编号")
    private String gonganBeianNumber;
}
