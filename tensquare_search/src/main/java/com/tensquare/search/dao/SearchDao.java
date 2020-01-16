package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchDao extends ElasticsearchRepository<Article,String> {
}
