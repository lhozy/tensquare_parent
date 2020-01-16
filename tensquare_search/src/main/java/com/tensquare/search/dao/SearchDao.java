package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchDao extends ElasticsearchRepository<Article,String> {
    public Page<Article> findAllByTitleOrContentLike(String title,String content, Pageable pageable);
}
