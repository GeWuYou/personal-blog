package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ResourceMapper;
import com.gewuyou.blog.admin.mapper.RoleResourceMapper;
import com.gewuyou.blog.admin.service.IResourceService;
import com.gewuyou.blog.common.dto.LabelOptionDTO;
import com.gewuyou.blog.common.dto.ResourceDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Resource;
import com.gewuyou.blog.common.model.RoleResource;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ResourceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;

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

    private final RoleResourceMapper roleResourceMapper;

    public ResourceServiceImpl(RoleResourceMapper roleResourceMapper) {
        this.roleResourceMapper = roleResourceMapper;
    }

    /**
     * 查询资源列表
     *
     * @param conditionVO 条件
     * @return 资源列表
     */
    @Override
    public List<ResourceDTO> listResourceDTOs(ConditionVO conditionVO) {
        // 获取所有资源列表
        List<Resource> resources = baseMapper.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Resource::getName,
                        conditionVO.getKeywords()));
        // 筛选出顶级资源列表
        List<Resource> parents = listTopResource(resources);
        // 获取子资源列表
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resources);
        // 将子资源绑定到父资源上
        List<ResourceDTO> resourceDTOs = parents.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtil.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> child = BeanCopyUtil.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(child);
            childrenMap.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 将剩余资源添加到资源列表中
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOs = childrenList.stream()
                    .map(item -> BeanCopyUtil.copyObject(item, ResourceDTO.class))
                    .toList();
            resourceDTOs.addAll(childrenDTOs);
        }
        return resourceDTOs;
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源ID
     */
    @Override
    public void deleteResource(Integer resourceId) {
        Long count = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new GlobalException(ResponseInformation.NON_EMPTY_ROLE_DELETION_REQUEST);
        }
        List<Integer> resourceIds = baseMapper.selectList(new LambdaQueryWrapper<Resource>()
                        .select(Resource::getId).
                        eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIds.add(resourceId);
        baseMapper.deleteBatchIds(resourceIds);
    }

    /**
     * 保存或更新资源
     *
     * @param resourceVO 资源VO
     */
    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {

    }

    /**
     * 查询资源选项
     *
     * @return 资源选项
     */
    @Override
    public List<LabelOptionDTO> listResourceOptionDTO() {
        List<Resource> resources = baseMapper.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, FALSE));
        List<Resource> parents = listTopResource(resources);
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resources);
        return parents.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).toList();
    }

    /**
     * 获取子资源
     *
     * @param resources 资源列表
     * @return 子资源列表
     */
    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resources) {
        return resources
                .stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }

    /**
     * 获取顶级资源
     *
     * @param resources 资源列表
     * @return 父模块列表
     */
    private List<Resource> listTopResource(List<Resource> resources) {
        return resources
                .stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .toList();
    }
}
