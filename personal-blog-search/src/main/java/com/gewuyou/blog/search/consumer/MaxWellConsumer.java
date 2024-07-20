package com.gewuyou.blog.search.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.EsArticleDTO;
import com.gewuyou.blog.common.dto.MaxwellDataDTO;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.search.repository.IEsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gewuyou.blog.common.constant.RabbitMQConstant.MAXWELL_QUEUE;

/**
 * Max Well 消费者
 *
 * @author gewuyou
 * @since 2024-07-20 下午3:16:18
 */
@Component
@RabbitListener(queues = MAXWELL_QUEUE)
@Slf4j
public class MaxWellConsumer {
    private final IEsRepository esRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MaxWellConsumer(IEsRepository esRepository, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.esRepository = esRepository;
    }

    @RabbitHandler
    public void process(byte[] data) {
        try {
            // 使用 Jackson 将 JSON 字符串解析为 MaxwellDataDTO 对象
            MaxwellDataDTO maxwellDataDTO = objectMapper.readValue(new String(data), MaxwellDataDTO.class);
            // 将 MaxwellDataDTO 对象中的 data 字段转换为 JSON 字符串，再解析为 Article 对象
            Article article = objectMapper.convertValue(maxwellDataDTO.getData(), Article.class);
            switch (maxwellDataDTO.getType()) {
                case "insert":
                case "update":
                    esRepository.save(BeanCopyUtil.copyObject(article, EsArticleDTO.class));
                    break;
                case "delete":
                    esRepository.deleteById(article.getId());
                    break;
                default:
                    break;
            }
        } catch (JsonProcessingException e) {
            log.error("解析 MaxwellDataDTO 对象出错", e);
        }
    }
}
