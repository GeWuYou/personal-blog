package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.admin.mapper.TalkMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.service.ITalkService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TalkAdminDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Talk;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.FileUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.TalkVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    private final ObjectMapper objectMapper;

    private final IRedisService redisService;

    private final IImageReferenceService imageReferenceService;

    @Autowired
    public TalkServiceImpl(ObjectMapper objectMapper, IRedisService redisService, IImageReferenceService imageReferenceService) {
        this.objectMapper = objectMapper;
        this.redisService = redisService;
        this.imageReferenceService = imageReferenceService;
    }

    /**
     * 保存或更新说说
     *
     * @param talkVO 说说VO
     */
    @Override
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public void saveOrUpdateTalk(TalkVO talkVO) {
        Talk talk = BeanCopyUtil.copyObject(talkVO, Talk.class);
        talk.setUserId(UserUtil.getUserDetailsDTO().getUserInfoId());
        String talkImages = talk.getImages();
        try {
            List<String> newImages = objectMapper
                    .readValue(talkImages, new TypeReference<List<String>>() {
                    })
                    .stream()
                    .map(FileUtil::getFilePathByUrl)
                    .toList();
            // 判断是新增还是更新
            if (Objects.nonNull(talk.getId())) {
                List<String> oldImages = objectMapper.readValue(baseMapper
                        .selectOne(new LambdaQueryWrapper<Talk>()
                                .select(Talk::getImages)
                                .eq(Talk::getId, talk.getId()))
                        .getImages(), new TypeReference<>() {
                });
                imageReferenceService.handleImageReferences(newImages, oldImages);
            } else {
                imageReferenceService.handleImageReferences(newImages, List.of());
            }
        } catch (JsonProcessingException e) {
            throw new GlobalException(ResponseInformation.JSON_PARSE_ERROR);
        }
        this.saveOrUpdate(talk);
    }

    /**
     * 删除说说
     *
     * @param talkIds 说说ID列表
     */
    @Override
    public void deleteTalks(List<Integer> talkIds) {
        talkIds.forEach(id -> {
            try {
                objectMapper
                        .readValue(
                                baseMapper
                                        .selectOne(new LambdaQueryWrapper<Talk>()
                                                .select(Talk::getImages)
                                                .eq(Talk::getId, id))
                                        .getImages(), new TypeReference<List<String>>() {
                                }).forEach(imageReferenceService::deleteImageReference);
            } catch (JsonProcessingException e) {
                throw new GlobalException(ResponseInformation.JSON_PARSE_ERROR);
            }
        });
        baseMapper.deleteBatchIds(talkIds);
    }

    /**
     * 分页查询说说列表
     *
     * @param conditionVO 条件VO
     * @return 分页结果DTO
     */
    @Override
    public PageResultDTO<TalkAdminDTO> listBackTalkAdminDTOs(ConditionVO conditionVO) {
        Long count = baseMapper.selectCount(
                new LambdaQueryWrapper<Talk>()
                        .eq(Objects.nonNull(conditionVO.getStatus()), Talk::getStatus, conditionVO.getStatus())
        );
        if (count == 0) {
            return new PageResultDTO<>();
        }
        Page<TalkAdminDTO> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        List<TalkAdminDTO> talkAdminDTOs = baseMapper.listTalkAdminDTOs(page, conditionVO).getRecords();
        talkAdminDTOs.forEach(item -> {
            if (StringUtils.isNotBlank(item.getImages())) {
                try {
                    item.setImageList(objectMapper.readValue(item.getImages(), new TypeReference<>() {
                    }));
                } catch (JsonProcessingException e) {
                    throw new GlobalException(ResponseInformation.JSON_PARSE_ERROR);
                }
            } else {
                item.setImageList(List.of());
            }
        });
        return new PageResultDTO<>(talkAdminDTOs, count);
    }

    /**
     * 根据ID获取后台说说DTO
     *
     * @param talkId 说说ID
     * @return 后台说说DTO
     */
    @Override
    public TalkAdminDTO getBackTalkById(Integer talkId) {
        TalkAdminDTO talkAdminDTO = baseMapper.getTalkAdminDTOById(talkId);
        if (StringUtils.isNotBlank(talkAdminDTO.getImages())) {
            try {
                talkAdminDTO.setImageList(objectMapper.readValue(talkAdminDTO.getImages(), new TypeReference<>() {
                }));
            } catch (JsonProcessingException e) {
                throw new GlobalException(ResponseInformation.JSON_PARSE_ERROR);
            }
        } else {
            talkAdminDTO.setImageList(List.of());
        }
        return talkAdminDTO;
    }
}
