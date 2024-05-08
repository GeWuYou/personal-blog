package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网站配置 VO
 *
 * @author gewuyou
 * @since 2024-05-05 下午7:31:27
 */
@Schema(description = "网站配置 VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebsiteConfigVO {
    /**
     * 网站名称
     */
    @Schema(description = "网站名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 网站作者昵称
     */
    @Schema(description = "网站作者昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String englishName;

    /**
     * 网站作者
     */
    @Schema(description = "网站作者", requiredMode = Schema.RequiredMode.REQUIRED)
    private String author;

    /**
     * 网站头像
     */
    @Schema(description = "网站头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorAvatar;

    /**
     * 网站作者介绍
     */
    @Schema(description = "网站作者介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorIntro;

    /**
     * 网站logo
     */
    @Schema(description = "网站logo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logo;

    /**
     * 多语言
     */
    @Schema(description = "多语言", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer multiLanguage;

    /**
     * 网站公告
     */
    @Schema(description = "网站公告", requiredMode = Schema.RequiredMode.REQUIRED)
    private String notice;

    /**
     * 网站创建时间
     */
    @Schema(description = "网站创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private String websiteCreateTime;

    /**
     * 网站备案号
     */
    @Schema(description = "网站备案号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String beianNumber;

    /**
     * QQ登录
     */
    @Schema(description = "QQ登录", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer qqLogin;

    /**
     * github
     */
    @Schema(description = "github", requiredMode = Schema.RequiredMode.REQUIRED)
    private String github;

    /**
     * 码云
     */
    @Schema(description = "码云", requiredMode = Schema.RequiredMode.REQUIRED)
    private String gitee;

    /**
     * qq
     */
    @Schema(description = "qq", requiredMode = Schema.RequiredMode.REQUIRED)
    private String qq;

    /**
     * 微信
     */
    @Schema(description = "微信", requiredMode = Schema.RequiredMode.REQUIRED)
    private String weChat;

    /**
     * 微博
     */
    @Schema(description = "微博", requiredMode = Schema.RequiredMode.REQUIRED)
    private String weibo;

    /**
     * csdn
     */
    @Schema(description = "csdn", requiredMode = Schema.RequiredMode.REQUIRED)
    private String csdn;

    /**
     * 知乎
     */
    @Schema(description = "知乎", requiredMode = Schema.RequiredMode.REQUIRED)
    private String zhihu;

    /**
     * 掘金
     */
    @Schema(description = "掘金", requiredMode = Schema.RequiredMode.REQUIRED)
    private String juejin;

    /**
     * 推特
     */
    @Schema(description = "推特", requiredMode = Schema.RequiredMode.REQUIRED)
    private String twitter;

    /**
     * stackoverflow
     */
    @Schema(description = "stackoverflow", requiredMode = Schema.RequiredMode.REQUIRED)
    private String stackoverflow;

    /**
     * 游客头像
     */
    @Schema(description = "游客头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String touristAvatar;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAvatar;


    /**
     * 是否评论审核
     */
    @Schema(description = "是否评论审核", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isCommentReview;

    /**
     * 是否邮箱通知
     */
    @Schema(description = "是否邮箱通知", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isEmailNotice;

    /**
     * 是否打赏
     */
    @Schema(description = "是否打赏", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isReward;
    /**
     * 微信二维码
     */
    @Schema(description = "微信二维码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String weiXinQRCode;

    /**
     * 支付宝二维码
     */
    @Schema(description = "支付宝二维码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alipayQRCode;

    /**
     * favicon
     */
    @Schema(description = "favicon", requiredMode = Schema.RequiredMode.REQUIRED)
    private String favicon;

    /**
     * 网页标题
     */
    @Schema(description = "网页标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String websiteTitle;

    /**
     * 公安部备案编号
     */
    @Schema(description = "公安部备案编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String gonganBeianNumber;
}
