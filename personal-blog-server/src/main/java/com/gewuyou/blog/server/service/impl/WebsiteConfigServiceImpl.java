package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.WebsiteConfig;
import com.gewuyou.blog.server.mapper.WebsiteConfigMapper;
import com.gewuyou.blog.server.service.IWebsiteConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站配置表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig> implements IWebsiteConfigService {

}
