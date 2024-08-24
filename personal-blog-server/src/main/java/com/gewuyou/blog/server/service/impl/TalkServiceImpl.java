package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.CommentCountDTO;
import com.gewuyou.blog.common.dto.TalkDTO;
import com.gewuyou.blog.common.entity.PageResult;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

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
    private final Executor asyncTaskExecutor;

    @Autowired
    public TalkServiceImpl(CommentMapper commentMapper, ObjectMapper objectMapper, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.commentMapper = commentMapper;
        this.objectMapper = objectMapper;
        this.asyncTaskExecutor = asyncTaskExecutor;
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
    public PageResult<TalkDTO> listTalkDTOs() {
        return CompletableFuture
                .supplyAsync(
                        () -> baseMapper.listTalkDTOs(new Page<>(PageUtil.getCurrent(), PageUtil.getSize())),
                        asyncTaskExecutor)
                .thenApply(talkDTOPage ->
                {
                    List<TalkDTO> records = talkDTOPage.getRecords();
                    Map<Integer, Long> commentCountMap = commentMapper
                            .listCommentCountByTypeAndTopicIds(
                                    CommentTypeEnum.TALK.getType(),
                                    records
                                            .stream()
                                            .map(TalkDTO::getId)
                                            .toList())
                            .stream()
                            .collect(Collectors
                                    .toMap(
                                            CommentCountDTO::getId, CommentCountDTO::getCommentCount));
                    records.stream()
                            .peek(
                                    item ->
                                            item
                                                    .setCommentCount(
                                                            commentCountMap.getOrDefault(item.getId(), 0L)))
                            .forEach(this::setImagesByTalkDTO);
                    return new PageResult<>(records, talkDTOPage.getTotal());
                })
                .exceptionally(e -> {
                    log.error("获取说说列表失败!", e);
                    return new PageResult<>();
                })
                .join();
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
