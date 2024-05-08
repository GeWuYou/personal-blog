package com.gewuyou.blog.common.listener;

import com.gewuyou.blog.common.event.OperationLogEvent;
import com.gewuyou.blog.common.model.OperationLog;
import com.gewuyou.blog.common.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 操作日志监听器
 *
 * @author gewuyou
 * @since 2024-05-03 下午11:40:07
 */
@Component
public class OperationLogListener implements ApplicationListener<OperationLogEvent> {
    private final IOperationLogService operationLogService;

    @Autowired
    public OperationLogListener(IOperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    /**
     * Handle an application event.
     *
     * @param operationLogEvent the event to respond to
     */
    @Async
    @Override
    public void onApplicationEvent(@NonNull OperationLogEvent operationLogEvent) {
        operationLogService.save((OperationLog) operationLogEvent.getSource());
    }
}
