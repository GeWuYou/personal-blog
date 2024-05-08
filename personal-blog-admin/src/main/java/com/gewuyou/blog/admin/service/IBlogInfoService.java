package com.gewuyou.blog.admin.service;

import com.gewuyou.blog.common.dto.BlogAdminInfoDTO;
import com.gewuyou.blog.common.dto.BlogHomeInfoDTO;

/**
 * 博客信息服务接口
 *
 * @author gewuyou
 * @since 2024-04-26 下午11:10:52
 */
public interface IBlogInfoService {

    /**
     * 上报访客信息
     */
    void report();

    /**
     * 获取博客后台首页信息
     *
     * @return 博客后台首页信息DTO
     */
    BlogHomeInfoDTO getBlogHomeInfo();

    /**
     * 获取博客管理后台信息
     *
     * @return 博客管理后台信息DTO
     */
    BlogAdminInfoDTO getBlogAdminInfo();
}
