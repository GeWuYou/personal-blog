package com.gewuyou.blog.search.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gewuyou.blog.common.dto.EsArticleDTO;
import com.gewuyou.blog.search.service.IEsArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.gewuyou.blog.common.constant.CommonConstant.*;
import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PUBLIC;

/**
 * Es 文章服务实现
 *
 * @author gewuyou
 * @since 2024-05-15 下午3:56:46
 */
@Slf4j
@Service
public class EsArticleServiceImpl implements IEsArticleService {

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public EsArticleServiceImpl(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return 文章列表
     */
    @Override
    public List<EsArticleDTO> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        return search(buildQuery(keywords));
    }

    /**
     * 分页搜索文章
     *
     * @param nativeQueryBuilder 查询对象
     * @return 文章列表
     */
    private List<EsArticleDTO> search(NativeQueryBuilder nativeQueryBuilder) {
        List<HighlightField> highlightFields = new ArrayList<>();
        HighlightField articleTitle = new HighlightField("articleTitle", HighlightFieldParameters.builder()
                .withPreTags(
                        PRE_TAG, POST_TAG
                ).build());
        highlightFields.add(articleTitle);
        HighlightField articleContent = new HighlightField("articleContent", HighlightFieldParameters.builder()
                .withPreTags(
                        PRE_TAG, POST_TAG
                )
                .withFragmentSize(50)
                .build());
        highlightFields.add(articleContent);
        Highlight highlight = new Highlight(highlightFields);
        nativeQueryBuilder.withHighlightQuery(new HighlightQuery(highlight, EsArticleDTO.class));
        try {
            SearchHits<EsArticleDTO> searchHits = elasticsearchTemplate.search(nativeQueryBuilder.build(),
                    EsArticleDTO.class);
            return searchHits.stream().map(hit -> {
                EsArticleDTO article = hit.getContent();
                List<String> highlights = hit.getHighlightFields().get("articleTitle");
                if (CollectionUtils.isNotEmpty(highlights)) {
                    article.setArticleTitle(highlights.get(0));
                }
                List<String> contentHighlights = hit.getHighlightFields().get("articleContent");
                if (CollectionUtils.isNotEmpty(contentHighlights)) {
                    article.setArticleContent(contentHighlights.get(0));
                }
                return article;
            }).toList();
        } catch (Exception e) {
            log.error("搜索文章失败", e);
        }
        return new ArrayList<>();
    }

    /**
     * 构建查询
     *
     * @param keywords 关键字
     * @return 查询对象
     */
    private NativeQueryBuilder buildQuery(String keywords) {
        NativeQueryBuilder queryBuilder = new NativeQueryBuilder();
        BoolQuery.Builder boolQuery = QueryBuilders.bool();
        boolQuery
                .must(
                        QueryBuilders.bool()
                                .should(
                                        QueryBuilders.match().queryName("articleTitle").query(keywords).build()._toQuery(),
                                        QueryBuilders.match().queryName("articleContent").query(keywords).build()._toQuery()
                                ).build()._toQuery()
                )
                .must(QueryBuilders.term().queryName("isDelete").value(FALSE).build()._toQuery())
                .must(QueryBuilders.term().queryName("status").value(PUBLIC.getStatus()).build()._toQuery())
        ;
        queryBuilder.withQuery(boolQuery.build()._toQuery());
        return queryBuilder;
    }


}
