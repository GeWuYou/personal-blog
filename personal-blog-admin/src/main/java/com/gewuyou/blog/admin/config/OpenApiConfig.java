package com.gewuyou.blog.admin.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 配置
 *
 * @author gewuyou
 * @since 2024-04-20 下午10:13:28
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("Personal-Blog-Admin API Document")
                        .description("Personal-Blog-Admin API Document")
                        .version("0.1.0")
                        .license(new License().name("GNU General Public License v2.0")
                                .url("https://www.gnu.org/licenses/gpl-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("欢迎访问gewuyou的github仓库")
                        .url("https://github.com/gewuyou"));
    }
}
