package com.gewuyou.blog.server.consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.EmailDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.model.UserInfo;
import com.gewuyou.blog.common.utils.EmailUtil;
import com.gewuyou.blog.server.service.IArticleService;
import com.gewuyou.blog.server.service.IUserInfoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gewuyou.blog.common.constant.CommonConstant.TRUE;
import static com.gewuyou.blog.common.constant.RabbitMQConstant.SUBSCRIBE_QUEUE;

/**
 * 订阅消费者
 *
 * @author gewuyou
 * @since 2024-05-08 下午7:46:40
 */
@Component
@RabbitListener(queues = SUBSCRIBE_QUEUE)
public class SubscribeConsumer {
    @Value("${website.url}")
    private String websiteUrl;

    private final IArticleService articleService;

    private final IUserInfoService userInfoService;

    private final EmailUtil emailUtil;

    private final ObjectMapper objectMapper;

    @Autowired
    public SubscribeConsumer(
            IArticleService articleService,
            IUserInfoService userInfoService,
            EmailUtil emailUtil,
            ObjectMapper objectMapper
    ) {
        this.articleService = articleService;
        this.userInfoService = userInfoService;
        this.emailUtil = emailUtil;
        this.objectMapper = objectMapper;
    }

    @RabbitHandler
    public void process(byte[] message) {
        try {
            Long articleId = objectMapper.readValue(message, Long.class);
            Article article = articleService.getOne(
                    new LambdaQueryWrapper<Article>()
                            .eq(Article::getArticleId, articleId)
            );
            List<UserInfo> userInfos = userInfoService.list(
                    new LambdaQueryWrapper<UserInfo>()
                            .eq(UserInfo::getIsSubscribe, TRUE)

            );
            List<String> emails = userInfos
                    .stream()
                    .map(UserInfo::getEmail)
                    .toList();
            for (String email : emails) {
                EmailDTO emailDTO = getEmailDTO(email, article);
                emailUtil.sendHtmlEmail(emailDTO);
            }

        } catch (IOException e) {
            throw new GlobalException(ResponseInformation.JSON_DESERIALIZE_ERROR);
        }
    }

    private @NotNull EmailDTO getEmailDTO(String email, Article article) {
        EmailDTO emailDTO = new EmailDTO();
        Map<String, Object> map = new HashMap<>();
        emailDTO.setEmail(email);
        emailDTO.setSubject("文章订阅");
        emailDTO.setTemplate("content.html");
        String url = websiteUrl + "/article/" + article.getArticleId();
        if (article.getUpdateTime() == null) {
            map.put("content", "gewuyou的个人博客发布了新的文章，"
                    + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
        } else {
            map.put("content", "gewuyou的个人博客对《" + article.getTitle() + "》进行了更新，"
                    + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
        }
        emailDTO.setContentMap(map);
        return emailDTO;
    }

}
