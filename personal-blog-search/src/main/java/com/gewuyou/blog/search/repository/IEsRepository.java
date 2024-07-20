package com.gewuyou.blog.search.repository;

import com.gewuyou.blog.common.dto.EsArticleDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Elasticsearch 仓库接口
 *
 * @author gewuyou
 * @since 2024-07-20 下午3:47:53
 */
@Repository
public interface IEsRepository extends ElasticsearchRepository<EsArticleDTO, Long> {
}
