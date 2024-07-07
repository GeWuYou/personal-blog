package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.model.About;
import com.gewuyou.blog.common.vo.AboutVO;

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
     * 更新关于信息
     *
     * @param aboutVO AboutVO
     */
    void updateAbout(AboutVO aboutVO);
}
