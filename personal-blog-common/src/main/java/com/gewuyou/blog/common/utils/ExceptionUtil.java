package com.gewuyou.blog.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常 工具
 *
 * @author gewuyou
 * @since 2024-05-04 上午12:43:17
 */
public class ExceptionUtil {
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
