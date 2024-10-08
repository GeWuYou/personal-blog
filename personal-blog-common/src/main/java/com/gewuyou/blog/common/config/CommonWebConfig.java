package com.gewuyou.blog.common.config;

import com.gewuyou.blog.common.interceptor.AccessLimitInterceptor;
import com.gewuyou.blog.common.interceptor.PaginationInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CommonWebConfig
 *
 * @author gewuyou
 * @since 2024-06-02 下午4:38:46
 */
@Configuration
public class CommonWebConfig implements WebMvcConfigurer {

    private final AccessLimitInterceptor accessLimitInterceptor;
    private final PaginationInterceptor paginationInterceptor;

    @Autowired
    public CommonWebConfig(AccessLimitInterceptor accessLimitInterceptor, PaginationInterceptor paginationInterceptor) {
        this.accessLimitInterceptor = accessLimitInterceptor;
        this.paginationInterceptor = paginationInterceptor;
    }

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry the InterceptorRegistry to add interceptors to
     */
    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
        registry.addInterceptor(paginationInterceptor);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
