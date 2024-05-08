package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.model.Talk;

/**
 * <p>
 * 说说表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ITalkService extends IService<Talk> {

    Long selectCount();
}
