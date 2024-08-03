package com.gewuyou.blog.admin.quartz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.UniqueViewMapper;
import com.gewuyou.blog.admin.mapper.UserAuthMapper;
import com.gewuyou.blog.admin.service.IJobLogService;
import com.gewuyou.blog.admin.service.IResourceService;
import com.gewuyou.blog.admin.service.IRoleResourceService;
import com.gewuyou.blog.common.dto.UserAreaDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.enums.RoleEnum;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.RoleResource;
import com.gewuyou.blog.common.model.UniqueView;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.DateUtil;
import com.gewuyou.blog.common.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.CommonConstant.UNKNOWN;
import static com.gewuyou.blog.common.constant.RedisConstant.*;

/**
 * 博客 quartz
 *
 * @author gewuyou
 * @since 2024-08-02 下午3:37:29
 */
@Slf4j
@Component("blogQuartz")
public class BlogQuartz {
    private final IRedisService redisService;
    private final UniqueViewMapper uniqueViewMapper;
    private final UserAuthMapper userAuthMapper;
    private final ObjectMapper jacksonObjectMapper;
    private final IJobLogService jobLogService;
    private final IResourceService resourceService;
    private final IRoleResourceService roleResourceService;

    @Autowired
    public BlogQuartz(IRedisService redisService, UniqueViewMapper uniqueViewMapper,
                      UserAuthMapper userAuthMapper, ObjectMapper jacksonObjectMapper, IJobLogService jobLogService, IResourceService resourceService, IRoleResourceService roleResourceService) {
        this.redisService = redisService;
        this.uniqueViewMapper = uniqueViewMapper;
        this.userAuthMapper = userAuthMapper;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.jobLogService = jobLogService;
        this.resourceService = resourceService;
        this.roleResourceService = roleResourceService;
    }

    /**
     * 保存每日访问量
     */
    public void saveUniqueView() {
        Long count = redisService.sSize(UNIQUE_VISITOR);
        log.info("每日访问量：{}", count);
        var uniqueView = UniqueView.builder()
                .createTime(DateUtil.offset(LocalDateTime.now(), -1,
                        ChronoUnit.DAYS)).viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        uniqueViewMapper.insert(uniqueView);
    }

    /**
     * 清理游客统计数据
     */
    public void clearVisitorStatistics() {
        redisService.delete(UNIQUE_VISITOR);
        redisService.delete(VISITOR_AREA);
    }

    /**
     * 统计用户地域分布
     */
    public void statisticalUserArea() {
        Map<String, Long> userAreaMap = userAuthMapper
                // 根据ip属地查询用户
                .selectList(
                        new LambdaQueryWrapper<UserAuth>()
                                .select(UserAuth::getIpSource)
                )
                .stream()
                // 检查用户的ip属地如果为空或者为空字符串，则设置为unknown
                .map(item -> (Objects.nonNull(item) && StringUtils.isNotBlank(item.getIpSource())) ?
                        IpUtil.getIpProvince(item.getIpSource()) : UNKNOWN)
                // 根据用户的ip属地进行分组统计
                .collect(Collectors.groupingBy(
                        item -> item,
                        Collectors.counting()
                ));
        List<UserAreaDTO> userAreaDTOList = userAreaMap
                .entrySet()
                .stream()
                .map(item -> UserAreaDTO
                        .builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build()
                ).toList();
        // 将统计结果保存到redis中
        try {
            redisService.set(USER_AREA, jacksonObjectMapper.writeValueAsString(userAreaDTOList));
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
        }
    }

    /**
     * 清理job日志
     */
    public void clearJobLogs() {
        jobLogService.cleanJobLogs();
    }

    /**
     * 导入swagger接口文档到数据库
     */
    public void importSwagger() {
        resourceService.importSwagger();
        List<RoleResource> roleResources = resourceService
                .list()
                .stream()
                .map(item -> RoleResource
                        .builder()
                        .roleId(RoleEnum.ADMIN.getRoleId())
                        .resourceId(item.getId())
                        .build()
                ).toList();
        roleResourceService.saveBatch(roleResources);
    }

}
