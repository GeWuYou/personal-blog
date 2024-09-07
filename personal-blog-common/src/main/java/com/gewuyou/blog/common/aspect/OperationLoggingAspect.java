package com.gewuyou.blog.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.dto.UserDetailsDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.event.OperationLogEvent;
import com.gewuyou.blog.common.model.OperationLog;
import com.gewuyou.blog.common.utils.IpUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.error("{}", ResponseInformation.LOG_BUILD_FAILED, e);
        } finally {
            startTime.remove();
        }
    }

    @AfterThrowing(pointcut = "@annotation(operationLogging)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, OperationLogging operationLogging, Exception e) {
        // 是否记录异常
        if (operationLogging.logException()) {
            return;
        }
        String resultStr = e.getMessage();
        // 保留前2000个字符
        if (resultStr.length() > 2000) {
            resultStr = resultStr.substring(0, 2000);
        }
        buildAndSaveLog(joinPoint, operationLogging, resultStr);
    }


    private void buildAndSaveLog(JoinPoint joinPoint, OperationLogging operationLogging, String resultStr) {
        var request = getCurrentHttpRequest();
        if (Objects.isNull(request)) {
            return;
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var userDetailsDTO = getUserDetailsDTO();
        // 如果是未登录用户，则不记录日志
        if (Objects.isNull(userDetailsDTO)) {
            return;
        }
        try {
            var operationLog = createOperationLog(joinPoint, operationLogging, resultStr, request, method, userDetailsDTO);
            // 保存日志
            applicationContext.publishEvent(new OperationLogEvent(operationLog));
        } catch (JsonProcessingException e) {
            log.error("{}", ResponseInformation.LOG_BUILD_FAILED, e);
        } finally {
            startTime.remove();
        }
    }

    /**
     * 创建操作日志对象
     *
     * @param joinPoint        切入点
     * @param operationLogging 操作日志注解
     * @param resultStr        返回结果
     * @param request          请求对象
     * @param method           方法对象
     * @param userDetailsDTO   用户详情DTO
     * @return 操作日志对象
     * @throws JsonProcessingException JSON 处理异常
     */
    private OperationLog createOperationLog(JoinPoint joinPoint, OperationLogging operationLogging, String resultStr, HttpServletRequest request, Method method, UserDetailsDTO userDetailsDTO) throws JsonProcessingException {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        String optMethod = className + "." + methodName;
        // 获取类注解Tag
        Tag tag = joinPoint.getTarget().getClass().getAnnotation(Tag.class);
        // 获取方法注解Operation
        Operation operation = method.getAnnotation(Operation.class);
        String ipAddress = IpUtil.getIpAddress(Objects.requireNonNull(request));
        String ipSource = IpUtil.getIpSource(ipAddress);

        // 是否记录请求参数
        String requestParam = null;
        if (operationLogging.logParams()) {
            requestParam = objectMapper.writeValueAsString(joinPoint.getArgs());
        }
        // 是否记录返回结果
        if (!operationLogging.logResult()) {
            resultStr = null;
        }
        // 打印日志在控制台
        log.info("""
                        
                        \r=======================================
                        \r\
                        请求地址:{}\s
                        \r\
                        请求方式:{}\s
                        \r\
                        请求类方法:{}\s
                        \r\
                        请求方法参数:{}\s
                        \r\
                        返回报文:{}\s
                        \r\
                        处理耗时:{} ms\s
                        \r\
                        =======================================
                        \r""",
                request.getRequestURI(),
                request.getMethod(),
                joinPoint.getSignature(),
                requestParam,
                resultStr,
                Duration.between(startTime.get(), LocalDateTime.now()).toMillis()
        );
        return OperationLog
                .builder()
                .optModule(tag.name())
                .optUrl(Objects.requireNonNull(request).getRequestURI())
                .optType(operationLogging.type().getValue())
                .optMethod(optMethod)
                .optDesc(operation.description())
                .requestMethod(Objects.requireNonNull(request).getMethod())
                .requestParam(requestParam)
                .responseData(resultStr)
                .userId(userDetailsDTO.getUserAuthId())
                .userName(userDetailsDTO.getUsername())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .time(String.valueOf(startTime.get()))
                .duration(Duration.between(startTime.get(), LocalDateTime.now()).toMillis())
                .build();
    }

    /**
     * 获取用户详情DTO
     *
     * @return 用户详情DTO
     */
    private UserDetailsDTO getUserDetailsDTO() {
        try {
            return UserUtil.getUserDetailsDTO();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前请求对象
     *
     * @return 当前请求对象
     */
    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return Objects.isNull(requestAttributes) ? null : (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }
}
