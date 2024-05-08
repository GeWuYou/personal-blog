package com.gewuyou.blog.common.event;

import com.gewuyou.blog.common.model.OperationLog;
import org.springframework.context.ApplicationEvent;

/**
 * 操作日志记录事件
 *
 * @author gewuyou
 * @since 2024-05-03 下午11:38:30
 */
public class OperationLogEvent extends ApplicationEvent {
    public OperationLogEvent(OperationLog operationLog) {
        super(operationLog);
    }
}
