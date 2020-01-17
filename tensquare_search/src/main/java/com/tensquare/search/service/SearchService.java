package com.tensquare.search.service;

import com.tensquare.search.dao.SearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class SearchService {
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private IdWorker idWorker;

    public Page<Article> findByKey(String key,int page,int size){
        return searchDao.findAllByTitleOrContentLike(key,key,PageRequest.of(page-1,size));
    }
    public void save(Article article){
        article.setId(idWorker.nextId()+"");
        searchDao.save(article);
    }
}
