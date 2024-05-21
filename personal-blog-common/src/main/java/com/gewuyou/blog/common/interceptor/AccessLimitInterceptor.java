package com.gewuyou.blog.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.annotation.AccessLimit;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.gewuyou.blog.common.constant.CommonConstant.APPLICATION_JSON;

/**
 * 访问限制拦截器
 *
 * @author gewuyou
 * @since 2024-05-19 下午4:39:17
 */
@Slf4j
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    private final IRedisService redisService;

    private final ObjectMapper objectMapper;

    @Autowired
    public AccessLimitInterceptor(
            IRedisService redisService,
            ObjectMapper objectMapper
    ) {
        this.redisService = redisService;
        this.objectMapper = objectMapper;
    }

    /**
     * Interception point before the execution of a handler. Called after
     * HandlerMapping determined an appropriate handler object, but before
     * HandlerAdapter invokes the handler.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending an HTTP error or writing a custom response.
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link AsyncHandlerInterceptor}.
     * <p>The default implementation returns {@code true}.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        // 获取方法上的 AccessLimit 注解
        AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
        if (accessLimit == null) {
            return true;
        }
        // 获取访问时间间隔
        int seconds = accessLimit.seconds();
        // 获取访问次数
        int maxCount = accessLimit.maxCount();
        // 设置键
        String key = IpUtil.getIpAddress(request) + "-" + handlerMethod.getMethod().getName();
        Long l = redisService.incrExpire(key, seconds);
        if (l > maxCount) {
            render(response, Result.failure("请求次数过多" + seconds + "请稍后再试"));
            log.warn("{}请求次数超过每{}秒{}次限制", key, seconds, maxCount);
            return false;
        }
        return true;
    }

    private void render(HttpServletResponse response, Result<?> result) throws Exception {
        response.setContentType(APPLICATION_JSON);
        OutputStream out = response.getOutputStream();
        String str = objectMapper.writeValueAsString(result);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
