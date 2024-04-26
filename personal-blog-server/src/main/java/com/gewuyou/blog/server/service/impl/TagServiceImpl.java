package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.Tag;
import com.gewuyou.blog.server.mapper.TagMapper;
import com.gewuyou.blog.server.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
