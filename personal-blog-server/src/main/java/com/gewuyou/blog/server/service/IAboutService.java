package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.AboutDTO;
import com.gewuyou.blog.common.model.About;

/**
 * <p>
 * 关于表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-05-05
 */
public interface IAboutService extends IService<About> {

    /**
     * 获取关于信息
     *
     * @return AboutDTO
     */
    AboutDTO getAbout();
}
