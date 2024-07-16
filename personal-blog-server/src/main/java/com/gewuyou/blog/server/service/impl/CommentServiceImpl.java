package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.enums.CommentTypeEnum;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.Comment;
import com.gewuyou.blog.common.model.Talk;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.utils.HTMLUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.common.vo.CommentVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.ReviewVO;
import com.gewuyou.blog.server.mapper.ArticleMapper;
import com.gewuyou.blog.server.mapper.CommentMapper;
import com.gewuyou.blog.server.mapper.TalkMapper;
import com.gewuyou.blog.server.mapper.UserInfoMapper;
import com.gewuyou.blog.server.service.ICommentService;
import com.gewuyou.blog.server.service.IWebsiteConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.CommonConstant.*;
import static com.gewuyou.blog.common.constant.RabbitMQConstant.EMAIL_EXCHANGE;
import static com.gewuyou.blog.common.enums.CommentTypeEnum.ARTICLE;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    private final IWebsiteConfigService websiteConfigService;
    @Value("${website.url}")
    private String websiteUrl;
    private static final List<Byte> types = new ArrayList<>();
    private final ArticleMapper articleMapper;
    private final TalkMapper talkMapper;
    private final UserInfoMapper userInfoMapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public CommentServiceImpl(
            ArticleMapper articleMapper,
            TalkMapper talkMapper,
            UserInfoMapper userInfoMapper,
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper,
            IWebsiteConfigService websiteConfigService) {
        this.articleMapper = articleMapper;
        this.talkMapper = talkMapper;
        this.userInfoMapper = userInfoMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.websiteConfigService = websiteConfigService;
    }

    @PostConstruct
    public void init() {
        CommentTypeEnum[] values = CommentTypeEnum.values();
        for (CommentTypeEnum value : values) {
            types.add(value.getType());
        }
    }

    @Override
    public Long selectCommentCountByType(Byte type) {
        return baseMapper
                .selectCount(
                        new LambdaQueryWrapper<Comment>()
                                .eq(Comment::getType, type)
                );
    }

    /**
     * 保存评论
     *
     * @param commentVO 评论信息
     */
    @Override
    public void saveComment(CommentVO commentVO) {
        this.checkCommentVO(commentVO);
        WebsiteConfigDTO websiteConfigDTO = websiteConfigService.getWebsiteConfig();
        Byte isCommentReview = websiteConfigDTO.getIsCommentReview();
        commentVO.setCommentContent(HTMLUtil.filter(commentVO.getCommentContent()));
        Comment comment = Comment
                .builder()
                .userId(UserUtil.getUserDetailsDTO().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isCommentReview)
                .build();
        baseMapper.insert(comment);
        String fromNickname = UserUtil.getUserDetailsDTO().getNickname();
        if (websiteConfigDTO.getIsEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> notice(comment, fromNickname));
        }
    }

    /**
     * 根据评论VO获取评论列表
     *
     * @param commentVO 评论VO
     * @return 评论列表
     */
    @Override
    public PageResultDTO<CommentDTO> listCommentDTOs(CommentVO commentVO) {
        Long commentCount = baseMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .eq(Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResultDTO<>();
        }
        Page<CommentDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<CommentDTO> commentDTOs = baseMapper.listCommentDTOs(page, commentVO);
        if (CollectionUtils.isEmpty(commentDTOs)) {
            return new PageResultDTO<>();
        }
        List<Long> commentIds = commentDTOs.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        List<ReplyDTO> replyDTOS = baseMapper.listReplyDTOs(commentIds);
        Map<Long, List<ReplyDTO>> replyMap = replyDTOS.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        commentDTOs.forEach(item -> item.setReplyDTOs(replyMap.get(item.getId())));
        return new PageResultDTO<>(commentDTOs, commentCount);
    }

    /**
     * 根据评论ID获取回复列表
     *
     * @param commentId 评论ID
     * @return replies
     */
    @Override
    public List<ReplyDTO> listReplyDTOsByCommentId(Long commentId) {
        return baseMapper.listReplyDTOs(Collections.singletonList(commentId));
    }

    /**
     * 获取前六条评论
     *
     * @return 评论列表
     */
    @Override
    public List<CommentDTO> listTopSixCommentDTOs() {
        return baseMapper.listTopSixCommentDTOs();
    }

    /**
     * 获取后台评论列表
     *
     * @param conditionVO 条件
     * @return 评论列表
     */
    @Override
    public PageResultDTO<CommentAdminDTO> listCommentsAdminDTOs(ConditionVO conditionVO) {
        CompletableFuture<Long> asyncCount =
                CompletableFuture.supplyAsync(() -> baseMapper.countComments(conditionVO));
        Page<CommentAdminDTO> page = new Page<>(PageUtil.getLimitCurrent(), PageUtil.getSize());
        List<CommentAdminDTO> commentAdminDTOS = baseMapper.listCommentAdminDTOs(page, conditionVO);
        try {
            return new PageResultDTO<>(commentAdminDTOS, asyncCount.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
        }
    }

    /**
     * 审核评论
     *
     * @param reviewVO 审核VO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        List<Comment> comments = reviewVO
                .getIds()
                .stream()
                .map(id -> Comment
                        .builder()
                        .id(id)
                        .isReview(reviewVO.getIsReview())
                        .build()
                )
                .toList();
        this.updateBatchById(comments);
    }

    /**
     * 通知
     *
     * @param comment      评论
     * @param fromNickname 发起者昵称
     */
    private void notice(Comment comment, String fromNickname) {
        if (Objects.isNull(comment.getParentId())) {
            return;
        }
        Comment parentComment = baseMapper.selectById(comment.getParentId());
        // 判断用户是否自己回复自己,防止套娃
        if (comment.getUserId().equals(comment.getReplyUserId()) && parentComment.getUserId().equals(comment.getUserId())) {
            return;
        }
        // 判断是否是管理员回复
        if (comment.getUserId().equals(BLOGGER_ID)) {
            return;
        }
        if (!comment.getReplyUserId().equals(parentComment.getUserId()) &&
                !comment.getReplyUserId().equals(comment.getUserId())) {
            UserInfo userInfo = userInfoMapper.selectById(comment.getUserId());
            UserInfo replyUserInfo = userInfoMapper.selectById(comment.getReplyUserId());
            Map<String, Object> map = new HashMap<>();
            String topicId = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
            String url = websiteUrl + CommentTypeEnum.getCommentPath(comment.getType()) + topicId;
            map.put("content", userInfo.getNickName() + "在" +
                    Objects.requireNonNull(CommentTypeEnum.getCommentEnum(comment.getType())).getDesc()
                    + "的评论区@了你，"
                    + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
            EmailDTO emailDTO = EmailDTO.builder()
                    .email(replyUserInfo.getEmail())
                    .subject(MENTION_REMIND)
                    .template("common.html")
                    .contentMap(map)
                    .build();
            try {
                rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*",
                        new Message(objectMapper.writeValueAsBytes(emailDTO),
                                new MessageProperties()));
            } catch (JsonProcessingException e) {
                log.error("通知失败", e);
                throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
            }
            // 防止套娃
            if (comment.getUserId().equals(parentComment.getUserId())) {
                return;
            }
        }
        String title;
        Long userId = BLOGGER_ID;
        String topicId = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
        if (Objects.nonNull(comment.getReplyUserId())) {
            userId = comment.getReplyUserId();
        } else {
            switch (Objects.requireNonNull(CommentTypeEnum.getCommentEnum(comment.getType()))) {
                case ARTICLE:
                    userId = articleMapper.selectById(comment.getTopicId()).getUserId();
                    break;
                case TALK:
                    userId = talkMapper.selectById(comment.getTopicId()).getUserId();
                default:
                    break;
            }
        }
        if (Objects.requireNonNull(CommentTypeEnum.getCommentEnum(comment.getType())).equals(ARTICLE)) {
            title = articleMapper.selectById(comment.getTopicId()).getArticleTitle();
        } else {
            title = Objects.requireNonNull(CommentTypeEnum.getCommentEnum(comment.getType())).getDesc();
        }
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (StringUtils.isNotBlank(userInfo.getEmail())) {
            EmailDTO emailDTO = getEmailDTO(comment, userInfo, fromNickname, topicId, title);
            try {
                rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(objectMapper.writeValueAsBytes(emailDTO), new MessageProperties()));
            } catch (JsonProcessingException e) {
                log.error("通知失败", e);
                throw new GlobalException(ResponseInformation.JSON_SERIALIZE_ERROR);
            }
        }
    }

    /**
     * 检查评论VO
     *
     * @param commentVO 评论信息
     */
    private void checkCommentVO(CommentVO commentVO) {
        // 检查评论类型是否合法
        if (!types.contains(commentVO.getType())) {
            throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
        }
        // 检查评论主题和文章是否存在
        if (
                ARTICLE.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType()))) ||
                        CommentTypeEnum.TALK.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType())))
        ) {
            if (Objects.isNull(commentVO.getTopicId())) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
            // 检查文章是否存在
            if (ARTICLE.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType())))) {
                Article article = articleMapper
                        .selectOne(
                                new LambdaQueryWrapper<Article>()
                                        .select(Article::getId, Article::getUserId)
                                        .eq(Article::getId, commentVO.getTopicId())
                        );
                if (Objects.isNull(article)) {
                    throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
                }
            }
            // 检查讨论是否存在
            if (CommentTypeEnum.TALK.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType())))) {
                Talk talk = talkMapper
                        .selectOne(
                                new LambdaQueryWrapper<Talk>()
                                        .select(Talk::getId, Talk::getUserId)
                                        .eq(Talk::getId, commentVO.getTopicId())
                        );
                if (Objects.isNull(talk)) {
                    throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
                }
            }
        }
        // 其它评论类型不应该有主题
        if (
                CommentTypeEnum.LINK.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType()))) ||
                        CommentTypeEnum.ABOUT.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType()))) ||
                        CommentTypeEnum.MESSAGE.equals(Objects.requireNonNull(CommentTypeEnum.getCommentEnum(commentVO.getType())))
        ) {
            if (Objects.nonNull(commentVO.getTopicId())) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
        }
        // 没有父评论id的评论不能有回复用户id
        if (Objects.isNull(commentVO.getParentId()) && Objects.nonNull(commentVO.getReplyUserId())) {
            throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
        }
        // 有父评论id的评论
        if (Objects.nonNull(commentVO.getParentId())) {
            Comment parentComment = baseMapper
                    .selectOne(
                            new LambdaQueryWrapper<Comment>()
                                    .select(Comment::getId, Comment::getParentId, Comment::getType)
                                    .eq(Comment::getId, commentVO.getParentId())
                    );
            // 父评论不存在
            if (Objects.isNull(parentComment)) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
            // 不允许套娃
            if (Objects.nonNull(parentComment.getParentId())) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
            // 评论类型必须一致
            if (!commentVO.getType().equals(parentComment.getType())) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
            // 有父评论id的评论必须有回复用户id
            if (Objects.isNull(commentVO.getReplyUserId())) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
            // 查看被回复者是否存在
            UserInfo userInfo = userInfoMapper
                    .selectOne(
                            new LambdaQueryWrapper<UserInfo>()
                                    .select(UserInfo::getId)
                                    .eq(UserInfo::getId, commentVO.getReplyUserId())
                    );
            if (Objects.isNull(userInfo)) {
                throw new GlobalException(ResponseInformation.PARAMETER_VALIDATION_EXCEPTION);
            }
        }
    }

    /**
     * 获取邮件DTO
     *
     * @param comment      评论
     * @param userInfo     用户信息
     * @param fromNickname 发起者昵称
     * @param topicId      主题ID
     * @param title        标题
     * @return 邮件DTO
     */
    private EmailDTO getEmailDTO(Comment comment, UserInfo userInfo, String fromNickname, String topicId, String title) {
        EmailDTO emailDTO = new EmailDTO();
        Map<String, Object> map = new HashMap<>();
        if (comment.getIsReview().equals(TRUE)) {
            String url = websiteUrl + CommentTypeEnum.getCommentPath(comment.getType()) + topicId;
            if (Objects.isNull(comment.getParentId())) {
                emailDTO.setEmail(userInfo.getEmail());
                emailDTO.setSubject(COMMENT_REMIND);
                emailDTO.setTemplate("owner.html");
                String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(comment.getCreateTime());
                map.put("time", createTime);
                map.put("url", url);
                map.put("title", title);
                map.put("nickname", fromNickname);
                map.put("content", comment.getCommentContent());
            } else {
                Comment parentComment = baseMapper.selectOne(
                        new LambdaQueryWrapper<Comment>()
                                .select(Comment::getUserId, Comment::getCommentContent, Comment::getCreateTime)
                                .eq(Comment::getId, comment.getParentId()));
                if (!userInfo.getId().equals(parentComment.getUserId())) {
                    userInfo = userInfoMapper.selectById(parentComment.getUserId());
                }
                emailDTO.setEmail(userInfo.getEmail());
                emailDTO.setSubject(COMMENT_REMIND);
                emailDTO.setTemplate("user.html");
                map.put("url", url);
                map.put("title", title);
                String createTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(parentComment.getCreateTime());
                map.put("time", createTime);
                map.put("toUser", userInfo.getNickName());
                map.put("fromUser", fromNickname);
                map.put("parentComment", parentComment.getCommentContent());
                if (!comment.getReplyUserId().equals(parentComment.getUserId())) {
                    UserInfo mentionUserInfo = userInfoMapper.selectById(comment.getReplyUserId());
                    if (Objects.nonNull(mentionUserInfo.getWebsite())) {
                        map.put("replyComment", "<a style=\"text-decoration:none;color:#12addb\" href=\""
                                + mentionUserInfo.getWebsite()
                                + "\">@" + mentionUserInfo.getNickName() + " " + "</a>" + parentComment.getCommentContent());
                    } else {
                        map.put("replyComment", "@" + mentionUserInfo.getNickName() + " " + parentComment.getCommentContent());
                    }
                } else {
                    map.put("replyComment", comment.getCommentContent());
                }
            }
        } else {
            String adminEmail = userInfoMapper.selectById(BLOGGER_ID).getEmail();
            emailDTO.setEmail(adminEmail);
            emailDTO.setSubject(CHECK_REMIND);
            emailDTO.setTemplate("common.html");
            map.put("content", "您收到了一条新的回复，请前往后台管理页面审核");
        }
        emailDTO.setContentMap(map);
        return emailDTO;
    }

}
