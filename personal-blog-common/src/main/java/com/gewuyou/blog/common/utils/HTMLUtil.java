package com.gewuyou.blog.common.utils;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;

/**
 * HTML工具类
 *
 * @author gewuyou
 * @since 2024-05-20 下午6:38:58
 */
public class HTMLUtil {

    private static final SensitiveWordBs sensitiveWordBs = SensitiveWordBs.newInstance()
            // 忽略大小写
            .ignoreCase(true)
            // 忽略(全角半角字符)宽度
            .ignoreWidth(true)
            // 忽略数字格式
            .ignoreNumStyle(true)
            // 忽略中文格式
            .ignoreChineseStyle(true)
            // 忽略英文格式
            .ignoreEnglishStyle(true)
            // 忽略重复
            .ignoreRepeat(true)
            // 关闭数字检查
            .enableNumCheck(false)
            // 关闭邮箱检查
            .enableEmailCheck(false)
            // 关闭url检查
            .enableUrlCheck(false)
            .init();

    /**
     * 移除HTML标签并移除onload和onerror事件属性
     *
     * @param source HTML字符串
     * @return 移除HTML标签后的字符串
     */
    public static String filter(String source) {
        source = source.replaceAll("(?!<(img).*?>)<.*?>", "")
                .replaceAll("(onload(.*?)=)", "")
                .replaceAll("(onerror(.*?)=)", "");
        return deleteHMTLTag(source);
    }

    /**
     * 移除HTML标签
     *
     * @param source HTML字符串
     * @return 移除HTML标签后的字符串
     */
    public static String deleteHMTLTag(String source) {
        source = source.replaceAll("&.{2,6}?;", "");
        source = source.replaceAll("<\\s*?script[^>]*?>[\\s\\S]*?<\\s*?/\\s*?script\\s*?>", "");
        source = source.replaceAll("<\\s*?style[^>]*?>[\\s\\S]*?<\\s*?/\\s*?style\\s*?>", "");
        return source;
    }
}
