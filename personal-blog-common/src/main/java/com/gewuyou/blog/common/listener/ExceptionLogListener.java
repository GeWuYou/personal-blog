package com.gewuyou.blog.common.listener;

import com.gewuyou.blog.common.event.ExceptionLogEvent;
import com.gewuyou.blog.common.model.ExceptionLog;
import com.gewuyou.blog.common.service.IExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异常日志监听器
 *
 * @author gewuyou
 * @since 2024-05-04 上午12:25:38
 */
@Component
public class ExceptionLogListener implements ApplicationListener<ExceptionLogEvent> {
    private final IExceptionLogService exceptionLogService;

    @Autowired
    public ExceptionLogListener(IExceptionLogService exceptionLogService) {
        this.exceptionLogService = exceptionLogService;
    }

    /**
     * Handle an application event.
     *
     * @param exceptionLogEvent the event to respond to
     */
    @Async
    @Override
    public void onApplicationEvent(@NonNull ExceptionLogEvent exceptionLogEvent) {
        exceptionLogService.save((ExceptionLog) exceptionLogEvent.getSource());
    }
}
