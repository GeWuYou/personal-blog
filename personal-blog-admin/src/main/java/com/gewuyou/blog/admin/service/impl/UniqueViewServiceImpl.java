package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.UniqueViewMapper;
import com.gewuyou.blog.admin.service.IUniqueViewService;
import com.gewuyou.blog.common.dto.UniqueViewDTO;
import com.gewuyou.blog.common.model.UniqueView;
import com.gewuyou.blog.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-05-04
 */
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements IUniqueViewService {

    /**
     * 获取所有唯一访问视图
     *
     * @return List<UniqueViewDTO>
     */
    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        var startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        var endTime = DateUtil.endOfDay(new Date());
        return baseMapper.listUniqueViews(startTime, endTime);
    }
}
