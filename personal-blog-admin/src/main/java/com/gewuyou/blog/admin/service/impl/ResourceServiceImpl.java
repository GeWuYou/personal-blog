package com.gewuyou.blog.admin.service.impl;


import com.gewuyou.blog.admin.mapper.ResourceMapper;
import com.gewuyou.blog.admin.service.IResourceService;
import com.gewuyou.blog.common.model.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
