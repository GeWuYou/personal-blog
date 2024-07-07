package com.gewuyou.blog.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.event.OperationLogEvent;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.OperationLog;
import com.gewuyou.blog.common.utils.IpUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 操作日志记录切面
 *
 * @author gewuyou
 * @since 2024-05-03 下午10:00:37
 */
@Aspect
@Component
public class OperationLoggingAspect {

    private final ObjectMapper objectMapper;

    private final ApplicationContext applicationContext;

    private final ThreadLocal<LocalDateTime> startTime = new ThreadLocal<>();

    @Autowired
    public OperationLoggingAspect(
            ObjectMapper objectMapper,
            ApplicationContext applicationContext
    ) {
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }

    @Before("@annotation(operationLogging)")
    public void before(OperationLogging operationLogging) {
        startTime.set(LocalDateTime.now());
    }

    @AfterReturning(pointcut = "@annotation(operationLogging)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, OperationLogging operationLogging, Object result) {
        try {
            String resultStr = objectMapper.writeValueAsString(result);
            buildAndSaveLog(joinPoint, operationLogging, resultStr);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.LOG_BUILD_FAILED, e);
        }
    }

    @AfterThrowing(pointcut = "@annotation(operationLogging)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, OperationLogging operationLogging, Exception e) {
        String resultStr = e.getMessage();
        // 保留前2000个字符
        if (resultStr.length() > 2000) {
            resultStr = resultStr.substring(0, 2000);
        }
        buildAndSaveLog(joinPoint, operationLogging, resultStr);
    }

    private void buildAndSaveLog(JoinPoint joinPoint, OperationLogging operationLogging, String resultStr) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        String optMethod = className + "." + methodName;
        // 获取类注解Tag
        Tag tag = joinPoint.getTarget().getClass().getAnnotation(Tag.class);
        // 获取方法注解Operation
        Operation operation = method.getAnnotation(Operation.class);
        UserDetailsDTO userDetailsDTO;
        // 如果是未登录用户，则不记录日志
        try {
            userDetailsDTO = UserUtil.getUserDetailsDTO();
        } catch (Exception e) {
            return;
        }
        String ipAddress = IpUtil.getIpAddress(Objects.requireNonNull(request));
        String ipSource = IpUtil.getIpSource(ipAddress);
        try {
            OperationLog operationLog = OperationLog
                    .builder()
                    .optModule(tag.name())
                    .optUrl(Objects.requireNonNull(request).getRequestURI())
                    .optType(operationLogging.type().getValue())
                    .optMethod(optMethod)
                    .optDesc(operation.description())
                    .requestMethod(Objects.requireNonNull(request).getMethod())
                    .requestParam(objectMapper.writeValueAsString(joinPoint.getArgs()))
                    .responseData(resultStr)
                    .userId(userDetailsDTO.getUserAuthId())
                    .userName(userDetailsDTO.getUsername())
                    .ipAddress(ipAddress)
                    .ipSource(ipSource)
                    .time(String.valueOf(startTime.get()))
                    .duration(Duration.between(startTime.get(), LocalDateTime.now()).toMillis())
                    .build();
            // 保存日志
            applicationContext.publishEvent(new OperationLogEvent(operationLog));
            startTime.remove();
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.LOG_BUILD_FAILED, e);
        }


    }
}
