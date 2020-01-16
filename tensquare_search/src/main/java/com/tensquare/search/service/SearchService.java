package com.tensquare.search.service;

import com.tensquare.search.dao.SearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private SearchDao searchDao;

    public Page<Article> findByKey(String key,int page,int size){
        return null;
    }
}
