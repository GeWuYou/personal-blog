package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.model.WebsiteConfig;

/**
 * <p>
 * 网站配置表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IWebsiteConfigService extends IService<WebsiteConfig> {

    /**
     * 获取网站配置
     *
     * @return 网站配置DTO
     */
    WebsiteConfigDTO getWebsiteConfig();
}
