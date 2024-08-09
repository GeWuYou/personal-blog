package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.CommentCountDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.TalkDTO;
import com.gewuyou.blog.common.enums.CommentTypeEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Talk;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.server.mapper.CommentMapper;
import com.gewuyou.blog.server.mapper.TalkMapper;
import com.gewuyou.blog.server.service.ITalkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PUBLIC;

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

    private final CommentMapper commentMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public TalkServiceImpl(CommentMapper commentMapper, ObjectMapper objectMapper) {
        this.commentMapper = commentMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long selectCount() {
        return baseMapper.selectCount(null);
    }

    /**
     * 获取说说列表
     *
     * @return 说说列表
     */
    @Override
    public PageResultDTO<TalkDTO> listTalkDTOs() {
        Long count = baseMapper.selectCount(
                new LambdaQueryWrapper<Talk>()
                        .eq(Talk::getStatus, PUBLIC.getStatus())
        );
        if (count == 0) {
            return new PageResultDTO<>();
        }
        Page<TalkDTO> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        List<TalkDTO> talkDTOS = baseMapper.listTalkDTOs(page).getRecords();
        List<Integer> talkIds = talkDTOS
                .stream()
                .map(TalkDTO::getId)
                .toList();
        Map<Integer, Long> commentCountMap = commentMapper
                .listCommentCountByTypeAndTopicIds(CommentTypeEnum.TALK.getType(), talkIds)
                .stream()
                .collect(Collectors.toMap(CommentCountDTO::getId, CommentCountDTO::getCommentCount));
        talkDTOS
                .forEach(
                        item -> {
                            item.setCommentCount(commentCountMap.getOrDefault(item.getId(), 0L));
                            setImagesByTalkDTO(item);
                        }
                );
        return new PageResultDTO<>(talkDTOS, count);
    }

    /**
     * 根据说说ID获取说说详情
     *
     * @param talkId 说说ID
     * @return 说说详情
     */
    @Override
    public TalkDTO getTalkById(Integer talkId) {
        TalkDTO talkDTO = baseMapper.getTalkDTOById(talkId);
        if (Objects.isNull(talkDTO)) {
            throw new GlobalException(ResponseInformation.TALK_NOT_EXIST);
        }
        setImagesByTalkDTO(talkDTO);
        CommentCountDTO commentCountDTO = commentMapper.listCommentCountByTypeAndTopicId(CommentTypeEnum.TALK.getType(), talkId);
        if (Objects.nonNull(commentCountDTO)) {
            talkDTO.setCommentCount(commentCountDTO.getCommentCount());
        }
        return talkDTO;
    }

    private void setImagesByTalkDTO(TalkDTO talkDTO) {
        if (StringUtils.isNotBlank(talkDTO.getImages())) {
            try {
                talkDTO.setImageList(objectMapper.readValue(talkDTO.getImages(), new TypeReference<>() {
                }));
            } catch (JsonProcessingException e) {
                log.error("json deserialize error", e);
                throw new GlobalException(ResponseInformation.JSON_DESERIALIZE_ERROR);
            }
        }
    }
}
