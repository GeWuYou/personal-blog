package com.gewuyou.blog.common.interceptor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.utils.PageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.Optional;

import static com.gewuyou.blog.common.constant.CommonConstant.*;

/**
 * 分页拦截器
 *
 * @author gewuyou
 * @since 2024-05-22 下午9:20:58
 */
@Component
public class PaginationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request
            , @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String currentPage = request.getParameter(CURRENT);
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        if (!Objects.isNull(currentPage) && !StringUtils.hasText(currentPage)) {
            PageUtil.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        PageUtil.remove();
    }
}
