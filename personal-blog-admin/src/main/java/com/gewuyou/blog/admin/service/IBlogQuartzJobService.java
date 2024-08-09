package com.gewuyou.blog.admin.service;

/**
 * 博客定时任务服务类
 *
 * @author gewuyou
 * @since 2024-08-09 12:09:05
 */
public interface IBlogQuartzJobService {
    /**
     * 清理临时图片
     */
    void clearTempImage();
}
