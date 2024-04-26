package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.About;
import com.gewuyou.blog.server.mapper.AboutMapper;
import com.gewuyou.blog.server.service.IAboutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关于表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements IAboutService {

}
