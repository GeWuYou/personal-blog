package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.Talk;
import com.gewuyou.blog.server.mapper.TalkMapper;
import com.gewuyou.blog.server.service.ITalkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 说说表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements ITalkService {

}
