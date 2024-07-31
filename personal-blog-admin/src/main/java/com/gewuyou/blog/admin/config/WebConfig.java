package com.gewuyou.blog.admin.config;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 *
 * @author gewuyou
 * @since 2024-07-31 下午2:25:38
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.mode}")
    private String mode;

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        if ("local".equals(mode)) {
            registry.addResourceHandler("/admin/blog/**")
                    .addResourceLocations("file:///D:/Project/JAVA/personal-blog/assets/blog/");
        }
    }
}
