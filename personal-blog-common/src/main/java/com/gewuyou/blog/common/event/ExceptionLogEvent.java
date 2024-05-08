package com.gewuyou.blog.common.event;

import com.gewuyou.blog.common.model.ExceptionLog;
import org.springframework.context.ApplicationEvent;

/**
 * 异常日志事件
 *
 * @author gewuyou
 * @since 2024-05-04 上午12:23:35
 */
public class ExceptionLogEvent extends ApplicationEvent {

    public ExceptionLogEvent(ExceptionLog exceptionLog) {
        super(exceptionLog);
    }
}
