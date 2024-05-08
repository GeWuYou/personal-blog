package com.gewuyou.blog.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.event.ExceptionLogEvent;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.ExceptionLog;
import com.gewuyou.blog.common.utils.ExceptionUtil;
import com.gewuyou.blog.common.utils.IpUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 异常日志切面
 *
 * @author gewuyou
 * @since 2024-05-04 上午12:08:56
 */
@Aspect
@Component
public class ExceptionLogAspect {
    private final ObjectMapper objectMapper;

    private final ApplicationContext applicationContext;

    @Autowired
    public ExceptionLogAspect(ObjectMapper objectMapper, ApplicationContext applicationContext) {
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }

    @Pointcut("execution(* com.gewuyou.blog.*.controller..*.*(..))")
    public void exceptionLogPointcut() {
    }

    @AfterThrowing(value = "exceptionLogPointcut()", throwing = "e")
    private void buildAndSaveLog(JoinPoint joinPoint, Exception e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String ipAddress = IpUtil.getIpAddress(Objects.requireNonNull(request));
        String ipSource = IpUtil.getIpSource(ipAddress);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        String optMethod = className + "." + methodName;
        Operation operation = method.getAnnotation(Operation.class);
        try {
            ExceptionLog exceptionLog = ExceptionLog.builder()
                    .optUri(Objects.requireNonNull(request).getRequestURI())
                    .optMethod(optMethod)
                    .requestMethod(request.getMethod())
                    .requestParam(objectMapper.writeValueAsString(joinPoint.getArgs()))
                    .optDesc(operation.description())
                    .exceptionInfo(ExceptionUtil.getTrace(e))
                    .ipAddress(ipAddress)
                    .ipSource(ipSource)
                    .build();
            applicationContext.publishEvent(new ExceptionLogEvent(exceptionLog));
        } catch (JsonProcessingException ex) {
            throw new GlobalException(ResponseInformation.LOG_BUILD_FAILED);
        }
    }
}
